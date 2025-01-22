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
import com.example.appbiblioteca.data.CreateLibroDto

class LibrosAdapter(
    private val libros: List<CreateLibroDto>,  // Lista de libros
    private val context: Context,
    private val onItemClick: (CreateLibroDto) -> Unit  // Función de clic para cada libro
) : RecyclerView.Adapter<LibrosAdapter.LibroViewHolder>() {

    // Inflar el layout de cada ítem (item_libros.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_libros, parent, false)
        return LibroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libros[position]
        holder.bind(libro)
    }

    override fun getItemCount(): Int = libros.size

    // ViewHolder para el libro
    inner class LibroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewLibro)
        private val textViewTitulo: TextView = itemView.findViewById(R.id.textViewTitulo)
        private val textViewAutor: TextView = itemView.findViewById(R.id.textViewAutor)
        private val cardView: CardView = itemView.findViewById(R.id.cardViewLibro)

        fun bind(libro: CreateLibroDto) {
            textViewTitulo.text = libro.titulo
            textViewAutor.text = libro.autor

            // Verifica si hay imágenes y luego carga la primera
            libro.imagenes?.firstOrNull()?.url?.let { url ->
                // Verificar si la URL no está vacía antes de cargarla
                if (url.isNotBlank()) {
                    Glide.with(context)
                        .load(url)  // Usamos la primera imagen de la lista
                        .placeholder(R.drawable.placeholder_image) // Imagen de carga
                        .error(R.drawable.error) // Imagen de error
                        .into(imageView)
                } else {
                    imageView.setImageResource(R.drawable.error)  // Si la URL está vacía, mostrar imagen de error
                }
            } ?: run {
                imageView.setImageResource(R.drawable.error)  // Si no hay imágenes, mostrar imagen de error
            }

            // Configurar el clic sobre el libro
            cardView.setOnClickListener {
                // Llamar a la función de clic con el libro actual
                onItemClick(libro)
            }
        }
    }
}
