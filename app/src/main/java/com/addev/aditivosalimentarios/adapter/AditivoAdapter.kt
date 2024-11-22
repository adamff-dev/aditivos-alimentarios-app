package com.addev.aditivosalimentarios.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.addev.aditivosalimentarios.R
import com.addev.aditivosalimentarios.model.Aditivo

class AditivoAdapter(private val aditivos: List<Aditivo>) :
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
        val aditivo = aditivos[position]
        holder.tvNumero.text = aditivo.numero
        holder.tvNombre.text = aditivo.nombre
        holder.tvToxicidad.text = aditivo.toxicidad

        if (aditivo.toxicidad == "Alta") {
            holder.tvToxicidad.setTextColor(Color.parseColor("#ff3d3d"))
        } else if (aditivo.toxicidad == "Media") {
            holder.tvToxicidad.setTextColor(Color.YELLOW)
        } else {
            holder.tvToxicidad.setTextColor(Color.parseColor("#00960d"))
        }
    }

    override fun getItemCount() = aditivos.size
}
