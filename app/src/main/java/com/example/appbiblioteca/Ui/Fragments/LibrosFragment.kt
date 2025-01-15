package com.example.appbiblioteca.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbiblioteca.R
import com.example.appbiblioteca.adaptadores.CategoriaAdapter
import com.example.appbiblioteca.data.Categoria
import com.example.appbiblioteca.data.Libros
import com.example.appbiblioteca.data.api.ApiService
import com.example.appbiblioteca.databinding.ActivityMainBinding
import com.example.appbiblioteca.databinding.FragmentLibrosBinding
import com.example.appbiblioteca.utils.RetrofitClient
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class LibrosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoriaAdapter: CategoriaAdapter
    private lateinit var categorias: List<Categoria>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_libros, container, false)

        // Configurar el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewCategorias)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Configurar el SearchView desde el layout incluido
        val searchView: androidx.appcompat.widget.SearchView = view.findViewById(R.id.searchView) // Usar androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Aquí puedes realizar alguna acción si es necesario cuando se envíe la búsqueda
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filtrar las categorías basadas en el texto de búsqueda
                val filteredCategorias = categorias.filter {
                    it.nombre.contains(newText ?: "", ignoreCase = true)
                }
                categoriaAdapter = CategoriaAdapter(filteredCategorias, requireContext()) { categoria ->
                    obtenerLibrosDeCategoria(categoria.id)
                }
                recyclerView.adapter = categoriaAdapter
                return true
            }
        })

        // Obtener las categorías desde la API
        obtenerCategorias()

        return view
    }

    private fun obtenerCategorias() {
        val apiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        apiService.getCategorias().enqueue(object : Callback<List<Categoria>> {
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                if (response.isSuccessful && response.body() != null) {
                    categorias = response.body()!!
                    categoriaAdapter = CategoriaAdapter(categorias, requireContext()) { categoria ->
                        obtenerLibrosDeCategoria(categoria.id)
                    }
                    recyclerView.adapter = categoriaAdapter
                }
            }

            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error al cargar las categorías", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun obtenerLibrosDeCategoria(categoriaId: Int) {
        val apiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        apiService.getLibrosByCategoria(categoriaId).enqueue(object : Callback<List<Libros>> {
            override fun onResponse(call: Call<List<Libros>>, response: Response<List<Libros>>) {
                if (response.isSuccessful && response.body() != null) {
                    val libros = response.body()!!
                    mostrarLibros(libros)
                }
            }

            override fun onFailure(call: Call<List<Libros>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error al cargar los libros", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarLibros(libros: List<Libros>) {
        // Aquí puedes mostrar los libros en otro fragmento o actividad
        Toast.makeText(requireContext(), "Libros cargados: ${libros.size}", Toast.LENGTH_SHORT).show()
    }
}
