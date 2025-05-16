package com.addev.aditivosalimentarios

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.addev.aditivosalimentarios.adapter.AditivoAdapter
import com.addev.aditivosalimentarios.dao.AditivoDao
import com.addev.aditivosalimentarios.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private val dbName = "additives.db"
    private val dbVersion = 2

    private lateinit var aditivoDao: AditivoDao
    private lateinit var etInput: EditText
    private lateinit var btnSearch: Button
    private lateinit var btnClear: Button
    private lateinit var rvResults: RecyclerView
    private lateinit var aditivoAdapter: AditivoAdapter
    private lateinit var loadingSpinner: ProgressBar;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInput = findViewById(R.id.etInput)
        btnSearch = findViewById(R.id.btnSearch)
        btnClear = findViewById(R.id.btnClear)
        rvResults = findViewById(R.id.rvResults)
        rvResults.layoutManager = LinearLayoutManager(this)
        loadingSpinner = findViewById(R.id.loadingSpinner)

        resetDatabaseIfNeeded(applicationContext, dbName, dbVersion)

        // Inicializa la base de datos y el DAO
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "additives.db"
        ).createFromAsset("additives.db")
            .build()

        aditivoDao = db.aditivoDao()

        loadAllAditivos()

        etInput.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                val input = etInput.text.toString()
                searchAditivos(input)
                true
            } else {
                false
            }
        }

        btnSearch.setOnClickListener {
            val input = etInput.text.toString()
            searchAditivos(input)
        }

        btnClear.setOnClickListener {
            etInput.setText("")
            searchAditivos("")
        }
    }

    fun showLoadingSpinner() {
        loadingSpinner.visibility = View.VISIBLE
    }

    fun hideLoadingSpinner() {
        loadingSpinner.visibility = View.GONE
    }

    private fun resetDatabaseIfNeeded(context: Context, dbName: String, currentVersion: Int) {
        val prefs = context.getSharedPreferences("db_prefs", Context.MODE_PRIVATE)
        val storedVersion = prefs.getInt("db_version", -1)

        if (storedVersion != currentVersion) {
            val dbFile = context.getDatabasePath(dbName)
            if (dbFile.exists()) dbFile.delete()

            prefs.edit().putInt("db_version", currentVersion).apply()
        }
    }

    private fun loadAllAditivos() {
        CoroutineScope(Dispatchers.IO).launch {
            val allAditivos =
                aditivoDao.getAllAditivos() // Método DAO para obtener todos los registros
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