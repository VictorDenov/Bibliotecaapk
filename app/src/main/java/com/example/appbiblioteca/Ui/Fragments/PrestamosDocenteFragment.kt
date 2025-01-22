package com.example.appbiblioteca.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.appbiblioteca.R
import com.example.appbiblioteca.data.CreateLibroDto

class PrestamosDocenteFragment : Fragment() {

    companion object {
        private const val ARG_LIBRO = "libro"

        // Función para crear una nueva instancia del fragmento, pasando el objeto libro
        fun newInstance(libro: CreateLibroDto): PrestamosDocenteFragment {
            val fragment = PrestamosDocenteFragment()
            val args = Bundle().apply {
                putParcelable(ARG_LIBRO, libro)  // Pasando el libro como Parcelable
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.fragment_prestamos_docente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el libro desde los argumentos
        val libro = arguments?.getParcelable<CreateLibroDto>(ARG_LIBRO)

        // Mostrar la información del libro
        libro?.let {
            // Puedes mostrar el título del libro en algún TextView
            view.findViewById<TextView>(R.id.textViewTituloDocente).text = it.titulo

            // Cargar la imagen del libro si está disponible
            it.imagenes.firstOrNull()?.url?.let { url ->
                if (url.isNotBlank()) {
                    Glide.with(requireContext())
                        .load(url)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error)
                } else {
                }
            } ?: run {
            }
        }

    }


}
