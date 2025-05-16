package com.addev.aditivosalimentarios

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.addev.aditivosalimentarios.model.AltName

class DetailsActivity : AppCompatActivity() {



    private fun formatParagraphs(text: String?): String {
        if (text.isNullOrBlank()) return "No disponible"
        // Reemplazar cada salto de línea simple por dos saltos para simular párrafo
        return text.replace("\n", "\n\n")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        val code = intent.getStringExtra("code")
        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val uses = intent.getStringExtra("uses")
        val sideEffects = intent.getStringExtra("sideEffects")
        val altNames = intent.getStringExtra("altNames")

        findViewById<TextView>(R.id.tvCode).text = formatParagraphs(code)
        findViewById<TextView>(R.id.tvName).text = formatParagraphs(name)
        findViewById<TextView>(R.id.tvDescription).text = formatParagraphs(description)
        findViewById<TextView>(R.id.tvUses).text = formatParagraphs(uses)
        findViewById<TextView>(R.id.tvSideEffects).text = formatParagraphs(sideEffects)
        findViewById<TextView>(R.id.tvAltNames).text = altNames

        val toxicity = intent.getStringExtra("toxicity")
        val tvToxicity = findViewById<TextView>(R.id.tvToxicity)

        tvToxicity.text = "Toxicidad: ${toxicity ?: "Desconocida"}"

        val badgeColor = when (toxicity?.uppercase()) {
            "ALTA" -> "#FF3D3D"
            "MEDIA" -> "#FFB300"
            "BAJA" -> "#00960D"
            else -> "#757575"
        }
        tvToxicity.setBackgroundColor(Color.parseColor(badgeColor))
    }
}