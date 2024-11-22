package com.addev.aditivosalimentarios.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.addev.aditivosalimentarios.dao.AditivoDao
import com.addev.aditivosalimentarios.model.Aditivo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

private const val ADITIVOS_ALIMENTARIOS_WEBSITE = "https://www.aditivos-alimentarios.com/"

class ScrappingService(private val aditivoDao: AditivoDao) {
    suspend fun fetchAditivosData(): List<Aditivo> {
        val aditivos = mutableListOf<Aditivo>()
        val document = Jsoup.connect(ADITIVOS_ALIMENTARIOS_WEBSITE).get()
        val rows = document.select("#tablelist tbody tr")

        for (row in rows) {
            val numero = row.select("td h2 a").text()
            val nombre = row.select("td h3 a").text()
            val toxicidad = row.select("td h4 span").text()
            aditivos.add(Aditivo(numero, nombre, toxicidad))
        }
        return aditivos
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    fun updateDatabaseIfNeeded(context: Context) {
        if (isInternetAvailable(context)) {
            CoroutineScope(Dispatchers.IO).launch {
                val newAditivos = fetchAditivosData()
                aditivoDao.insertAll(newAditivos)
            }
        }
    }
}
