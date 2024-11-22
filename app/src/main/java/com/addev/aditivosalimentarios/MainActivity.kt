package com.addev.aditivosalimentarios

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.addev.aditivosalimentarios.adapter.AditivoAdapter
import com.addev.aditivosalimentarios.dao.AditivoDao
import com.addev.aditivosalimentarios.database.AppDatabase
import com.addev.aditivosalimentarios.service.ScrappingService
import com.addev.aditivosalimentarios.ui.theme.AditivosAlimentariosTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var aditivoDao: AditivoDao
    private lateinit var scrappingService: ScrappingService
    private lateinit var etInput: EditText
    private lateinit var btnSearch: Button
    private lateinit var btnClear: Button
    private lateinit var rvResults: RecyclerView
    private lateinit var aditivoAdapter: AditivoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInput = findViewById(R.id.etInput)
        btnSearch = findViewById(R.id.btnSearch)
        btnClear = findViewById(R.id.btnClear)
        rvResults = findViewById(R.id.rvResults)

        // Inicializa la base de datos y el DAO
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "aditivos-db"
        ).build()
        aditivoDao = db.aditivoDao()
        scrappingService = ScrappingService(aditivoDao)
        scrappingService.updateDatabaseIfNeeded(this)
        aditivoAdapter = AditivoAdapter(emptyList())

        // Configura el RecyclerView
        rvResults.layoutManager = LinearLayoutManager(this)

        // Carga inicial de todos los registros
        loadAllAditivos()

        btnSearch.setOnClickListener {
            val input = etInput.text.toString()
            searchAditivos(input)
        }

        btnClear.setOnClickListener {
            etInput.setText("")
            searchAditivos("")
        }
    }

    private fun loadAllAditivos() {
        CoroutineScope(Dispatchers.IO).launch {
            val allAditivos = aditivoDao.getAllAditivos() // Método DAO para obtener todos los registros
            withContext(Dispatchers.Main) {
                aditivoAdapter = AditivoAdapter(allAditivos)
                rvResults.adapter = aditivoAdapter
            }
        }
    }

    private fun searchAditivos(input: String) {
        if (input.isEmpty()) {
            loadAllAditivos();
        }

        val terms = input.split(",").map { it.trim() }.filter { it.isNotEmpty() }

        if (terms.isEmpty()) {
            loadAllAditivos();
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val results = aditivoDao.findByNumbersOrNames(terms)
                withContext(Dispatchers.Main) {
                    // Verifica si hay resultados antes de asignar el adaptador
                    aditivoAdapter = AditivoAdapter(results)
                    rvResults.adapter = aditivoAdapter
                }
            }
        }
    }

}