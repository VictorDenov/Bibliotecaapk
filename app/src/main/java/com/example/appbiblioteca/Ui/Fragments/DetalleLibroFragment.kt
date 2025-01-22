package com.example.appbiblioteca.Ui.Fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.appbiblioteca.R
import com.example.appbiblioteca.data.CreateLibroDto

class DetalleLibroFragment : Fragment() {

    companion object {
        private const val ARG_LIBRO = "libro"

        // Función para crear una nueva instancia del fragmento, pasando el objeto libro
        fun newInstance(libro: CreateLibroDto): DetalleLibroFragment {
            val fragment = DetalleLibroFragment()
            val args = Bundle().apply {
                putParcelable(ARG_LIBRO, libro)  // Correcto, ya que CreateLibroDto es Parcelable
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
        return inflater.inflate(R.layout.fragment_detalle_libro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el libro desde los argumentos
        val libro = arguments?.getParcelable<CreateLibroDto>(ARG_LIBRO)

        // Mostrar la información del libro
        libro?.let {
            // Mostrar el título del libro
            view.findViewById<TextView>(R.id.textViewTituloDetalle).text = it.titulo

            // Cargar la imagen del libro
            it.imagenes.firstOrNull()?.url?.let { url ->
                if (url.isNotBlank()) {
                    Glide.with(requireContext())
                        .load(url)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error)
                        .into(view.findViewById(R.id.imageViewLibroDetalle))
                } else {
                    view.findViewById<ImageView>(R.id.imageViewLibroDetalle)
                        .setImageResource(R.drawable.error)
                }
            } ?: run {
                view.findViewById<ImageView>(R.id.imageViewLibroDetalle)
                    .setImageResource(R.drawable.error)
            }
        }

        // Botón para iniciar el préstamo
        val btnPrestar = view.findViewById<Button>(R.id.buttonPrestarLibro)
        btnPrestar.setOnClickListener {
            // Si el libro no es nulo, muestra el diálogo para elegir el tipo de usuario
            libro?.let {
                mostrarDialogoTipoUsuario(it)
            }
        }
    }

    // Función para mostrar el diálogo donde se elige el tipo de usuario
    private fun mostrarDialogoTipoUsuario(libro: CreateLibroDto) {
        val options = arrayOf("Estudiante", "Docente")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Selecciona el tipo de usuario")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        // Si elige Estudiante, redirige a PrestamosEstudianteFragment
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, PrestamosEstudianteFragment.newInstance(libro))
                            .addToBackStack(null)
                            .commit()
                    }
                    1 -> {
                        // Si elige Docente, redirige a PrestamosDocenteFragment
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, PrestamosDocenteFragment.newInstance(libro))
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        builder.show()
    }
}
