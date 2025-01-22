package com.example.appbiblioteca.adaptadores

import android.annotation.SuppressLint
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
        holder.bind(categoria)
    }

    override fun getItemCount(): Int = categorias.size

    // ViewHolder para la categoría
    inner class CategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreCategoria: TextView = itemView.findViewById(R.id.nombreCategoria)
        private val cardView: CardView = itemView.findViewById(R.id.cardViewCategoria)


        fun bind(categoria: Categoria) {
            nombreCategoria.text = categoria.nombre

            // Verifica si hay imágenes y luego carga la primera
            categoria.imagenes.firstOrNull()?.url?.let { url ->
                Glide.with(context)
                    .load(url)  // Usamos la primera imagen de la lista
                    .placeholder(R.drawable.placeholder_image) // Imagen de carga
                    .error(R.drawable.error) // Imagen de erro
            } ?: run {
            }

            // Configurar el clic sobre la categoría
            cardView.setOnClickListener {
                onItemClick(categoria)
            }
        }
    }
}
