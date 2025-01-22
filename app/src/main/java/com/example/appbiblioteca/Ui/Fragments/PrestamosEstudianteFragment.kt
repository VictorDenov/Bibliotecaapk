package com.example.appbiblioteca.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appbiblioteca.R
import com.example.appbiblioteca.data.CreateLibroDto

class PrestamosEstudianteFragment : Fragment() {

    companion object {
        private const val ARG_LIBRO = "libro"

        fun newInstance(libro: CreateLibroDto): PrestamosEstudianteFragment {
            val fragment = PrestamosEstudianteFragment()
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
        return inflater.inflate(R.layout.fragment_prestamos_estudiante, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val libro = arguments?.getParcelable<CreateLibroDto>(ARG_LIBRO)

        // Puedes usar el objeto libro para mostrar más información o realizar operaciones
        libro?.let {
            // Mostrar la información del libro en el fragmento de préstamo
        }
    }
}
