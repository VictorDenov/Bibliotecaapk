package com.example.appbiblioteca.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appbiblioteca.R
import com.example.appbiblioteca.data.Categoria

class CategoriaAdapter(
    private val categorias: List<Categoria>,
    private val context: Context,
    private val onItemClick: (Categoria) -> Unit
) : RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.nombreCategoria.text = categoria.nombre

        // Verifica si hay imágenes y luego carga la primera
        if (categoria.imagenes.isNotEmpty()) {
            Glide.with(context)
                .load(categoria.imagenes[0].url) // Usamos la primera imagen de la lista
                .placeholder(R.drawable.placeholder_image) // Imagen de carga
                .error(R.drawable.error) // Imagen de error
                .into(holder.imagenCategoria)
        } else {
            holder.imagenCategoria.setImageResource(R.drawable.placeholder_image) // Si no hay imagen, muestra una predeterminada
        }

        // Configurar el clic sobre la categoría
        holder.cardView.setOnClickListener {
            onItemClick(categoria)
        }
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreCategoria: TextView = itemView.findViewById(R.id.nombreCategoria)
        val imagenCategoria: ImageView = itemView.findViewById(R.id.imagenCategoria)
        val cardView: CardView = itemView.findViewById(R.id.cardViewCategoria)
    }
}
