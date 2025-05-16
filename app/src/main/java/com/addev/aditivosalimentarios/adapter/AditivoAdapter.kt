package com.addev.aditivosalimentarios.adapter

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.addev.aditivosalimentarios.DetailsActivity
import com.addev.aditivosalimentarios.R
import com.addev.aditivosalimentarios.databinding.DetailsActivityBinding
import com.addev.aditivosalimentarios.model.AdditiveWithAltNames


class AditivoAdapter(private val additives: List<AdditiveWithAltNames>) :
    RecyclerView.Adapter<AditivoAdapter.AditivoViewHolder>() {

    class AditivoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNumero: TextView = view.findViewById(R.id.tvNumero)
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvToxicidad: TextView = view.findViewById(R.id.tvToxicidad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AditivoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aditivo, parent, false)
        return AditivoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AditivoViewHolder, position: Int) {
        val aditivo = additives[position]
        holder.tvNumero.text = aditivo.additive?.code
        holder.tvNombre.text = aditivo.additive?.name
        holder.tvToxicidad.text = aditivo.additive?.toxicity

        // Cambiar color según la toxicidad
        if (aditivo.additive?.toxicity == "ALTA") {
            holder.tvToxicidad.setTextColor(Color.parseColor("#ff3d3d"))
        } else if (aditivo.additive?.toxicity == "MEDIA") {
            holder.tvToxicidad.setTextColor(Color.parseColor("#b97200"))
        } else if (aditivo.additive?.toxicity == "BAJA") {
            holder.tvToxicidad.setTextColor(Color.parseColor("#00960d"))
        } else {
            holder.tvToxicidad.setTextColor(Color.BLACK)
        }

        // Configurar el clic para abrir el navegador
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailsActivity::class.java).apply {
                putExtra("code", aditivo.additive?.code ?: "")
                putExtra("name", aditivo.additive?.name ?: "")
                putExtra("description", aditivo.additive?.description ?: "Sin descripción")
                putExtra("uses", aditivo.additive?.uses ?: "No disponible")
                putExtra("sideEffects", aditivo.additive?.sideEffects ?: "No disponible")
                putExtra("toxicity", aditivo.additive?.toxicity ?: "")
                putExtra("altNames", aditivo.altNames?.joinToString { n -> n.altName })

            }
            context.startActivity(intent)
        }
    }


    override fun getItemCount() = additives.size
}
