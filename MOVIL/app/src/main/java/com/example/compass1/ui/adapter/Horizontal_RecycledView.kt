package com.example.compass1.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.compass1.R

class Horizontal_RecycledView(private val listaDeRecursos: List<Int>) : RecyclerView.Adapter<Horizontal_RecycledView.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return listaDeRecursos.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recursoImagen = listaDeRecursos[position]
        holder.imageView.setImageResource(recursoImagen)
    }

    class MyViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {
        val imageView = vista.findViewById<ImageView>(R.id.imageView_1)
    }
}
