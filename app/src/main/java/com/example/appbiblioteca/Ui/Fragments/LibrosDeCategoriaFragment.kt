package com.example.appbiblioteca.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbiblioteca.R
import com.example.appbiblioteca.adaptadores.LibrosAdapter
import com.example.appbiblioteca.data.Categoria
import com.example.appbiblioteca.data.CreateLibroDto
import com.example.appbiblioteca.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibrosDeCategoriaFragment : Fragment() {

    private lateinit var categoria: Categoria
    private lateinit var libroAdapter: LibrosAdapter
    private lateinit var recyclerViewLibros: RecyclerView
    private var libros: List<CreateLibroDto> = emptyList()

    // Método para crear una nueva instancia del fragmento
    companion object {
        fun newInstance(categoria: Categoria): LibrosDeCategoriaFragment {
            val fragment = LibrosDeCategoriaFragment()
            val bundle = Bundle()
            bundle.putParcelable("categoria", categoria)  // Pasar la categoría como Parcelable
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_libros_de_categoria, container, false)

        // Recupera la categoría del Bundle
        categoria = arguments?.getParcelable("categoria") ?: Categoria(0, "", emptyList())

        // Configurar el RecyclerView para mostrar los libros
        recyclerViewLibros = view.findViewById(R.id.recyclerViewLibros)
        recyclerViewLibros.layoutManager = GridLayoutManager(context, 2)  // Usar GridLayoutManager para mostrar en formato de tarjetas

        // Llamamos a la API para obtener los libros de la categoría seleccionada
        obtenerLibrosDeCategoria(categoria.id)

        return view
    }

    private fun obtenerLibrosDeCategoria(categoriaId: Int) {
        val apiService = RetrofitClient.apiService

        // Hacemos la solicitud para obtener los libros de la categoría
        apiService.getLibrosDeCategoria(categoriaId).enqueue(object : Callback<List<CreateLibroDto>> {
            override fun onResponse(call: Call<List<CreateLibroDto>>, response: Response<List<CreateLibroDto>>) {
                if (response.isSuccessful) {
                    libros = response.body() ?: emptyList()

                    // Si no hay libros en la categoría, mostramos un mensaje
                    if (libros.isEmpty()) {
                        Toast.makeText(requireContext(), "No hay libros disponibles en esta categoría", Toast.LENGTH_SHORT).show()
                    } else {
                        // Configurar el adaptador con los libros de la categoría
                        libroAdapter = LibrosAdapter(libros, requireContext()) { libro ->
                            // Lógica cuando se hace clic en un libro
                            val fragment = DetalleLibroFragment.newInstance(libro)  // Pasar el libro como argumento
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.frame_layout, fragment)
                                .addToBackStack(null)
                                .commit()
                        }
                        recyclerViewLibros.adapter = libroAdapter
                    }
                } else {
                    Toast.makeText(requireContext(), "Error al cargar los libros", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CreateLibroDto>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
