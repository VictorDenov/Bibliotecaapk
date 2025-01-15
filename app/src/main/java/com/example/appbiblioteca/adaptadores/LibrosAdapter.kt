package com.example.appbiblioteca.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appbiblioteca.R

import com.example.appbiblioteca.data.Libros
import com.example.appbiblioteca.databinding.ItemLibroBinding

class LibrosAdapter(private val libros: List<Libros>) : RecyclerView.Adapter<LibrosAdapter.LibroViewHolder>() {

    // Inflar el layout de cada ítem (item_libro.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_libro, parent, false)
        return LibroViewHolder(view)
    }

    // Vincular los datos con el layout del ítem
    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libros[position]
        holder.bind(libro)
    }

    // Retornar la cantidad de ítems
    override fun getItemCount(): Int = libros.size

    class LibroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewLibro: ImageView = itemView.findViewById(R.id.imageViewLibro)
        private val textViewTitulo: TextView = itemView.findViewById(R.id.textViewTitulo)
        private val textViewAutor: TextView = itemView.findViewById(R.id.textViewAutor)
        private val textViewCategoria: TextView = itemView.findViewById(R.id.textViewCategoria)

        fun bind(libros: Libros) {
            // Aquí puedes cargar la imagen usando una librería como Glide o Picasso
            // Glide.with(itemView.context).load(libro.imageUrl).into(imageViewLibro)
            textViewTitulo.text = libros.titulo
            textViewAutor.text = libros.autor
        }
    }
}
