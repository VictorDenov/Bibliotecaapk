package com.example.appbiblioteca.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbiblioteca.R
import com.example.appbiblioteca.adaptadores.CategoriaAdapter
import com.example.appbiblioteca.adaptadores.LibrosAdapter
import com.example.appbiblioteca.data.Categoria
import com.example.appbiblioteca.data.CreateLibroDto
import com.example.appbiblioteca.data.api.ApiService
import com.example.appbiblioteca.databinding.ActivityMainBinding
import com.example.appbiblioteca.databinding.FragmentLibrosBinding
import com.example.appbiblioteca.utils.RetrofitClient

import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call;



class LibrosFragment : Fragment() {

    private lateinit var recyclerViewLibros: RecyclerView
    private lateinit var recyclerViewCategorias: RecyclerView
    private lateinit var categoriaAdapter: CategoriaAdapter
    private var categorias: List<Categoria> = emptyList() // Inicializado con una lista vacía

    private lateinit var libroAdapter: LibrosAdapter
    private var libros: List<CreateLibroDto> = emptyList() // Inicializado con una lista vacía

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_libros, container, false)

        // Configuración del RecyclerView para las categorías
        recyclerViewCategorias = view.findViewById(R.id.recyclerViewCategorias)
        recyclerViewCategorias.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Configuración del RecyclerView para los libros
        recyclerViewLibros = view.findViewById(R.id.recyclerViewLibros)
        recyclerViewLibros.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        setHasOptionsMenu(true)

        // Configuración del SearchView
        val searchView: androidx.appcompat.widget.SearchView = view.findViewById(R.id.searchView)

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (isAdded) { // Verificar si el fragmento está adjunto
                    // Filtrar las categorías por el texto ingresado
                    val filteredCategorias = categorias.filter {
                        it.nombre.contains(newText ?: "", ignoreCase = true)
                    }
                    // Actualizar el adaptador de categorías
                    categoriaAdapter = CategoriaAdapter(filteredCategorias, requireContext()) { categoria ->
                        // Lógica de selección de categoría (si es necesario)
                    }
                    recyclerViewCategorias.adapter = categoriaAdapter
                }
                return true
            }
        })

        // Obtener las categorías y libros desde la API
        obtenerCategorias()
        obtenerLibros()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar, menu)  // Inflar el menú desde el archivo XML
    }

    // Manejar las selecciones de los íconos en el menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigationIcon -> {
                if (isAdded) { // Verificar si el fragmento está adjunto
                    Toast.makeText(requireContext(), "Menú clickeado", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            R.id.profileIcon -> {
                if (isAdded) { // Verificar si el fragmento está adjunto
                    Toast.makeText(requireContext(), "Perfil clickeado", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun obtenerCategorias() {
        val apiService = RetrofitClient.apiService

        apiService.getCategorias().enqueue(object : Callback<List<Categoria>> {
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                if (isAdded) { // Verificar si el fragmento está adjunto
                    if (response.isSuccessful && response.body() != null) {
                        categorias = response.body() ?: emptyList()
                        // Actualizar el adaptador con la lista de categorías obtenidas
                        categoriaAdapter = CategoriaAdapter(categorias, requireContext()) { categoria ->
                            // Lógica de selección de categoría (si es necesario)
                        }
                        recyclerViewCategorias.adapter = categoriaAdapter
                    } else {
                        Toast.makeText(requireContext(), "Error al cargar las categorías", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                if (isAdded) { // Verificar si el fragmento está adjunto
                    Toast.makeText(requireContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun obtenerLibros() {
        val apiService = RetrofitClient.apiService

        apiService.getLibros().enqueue(object : Callback<List<CreateLibroDto>> {
            override fun onResponse(call: Call<List<CreateLibroDto>>, response: Response<List<CreateLibroDto>>) {
                if (isAdded) { // Verificar si el fragmento está adjunto
                    if (response.isSuccessful) {
                        response.body()?.let { librosList ->
                            libros = librosList // Asignamos la lista completa de libros
                            libroAdapter = LibrosAdapter(libros, requireContext()) { libro ->
                                // Navegar al DetalleLibroFragment con el libro seleccionado
                                val fragment = DetalleLibroFragment.newInstance(libro)
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.frame_layout, fragment)  // 'frame_layout' es el ID del contenedor del fragmento
                                    .addToBackStack(null)  // Permitir la navegación hacia atrás
                                    .commit()
                            }
                            recyclerViewLibros.adapter = libroAdapter // Establecemos el adaptador
                        }
                    } else {
                        if (isAdded) {
                            Toast.makeText(requireContext(), "Error en la respuesta", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CreateLibroDto>>, t: Throwable) {
                if (isAdded) { // Verificar si el fragmento está adjunto
                    Toast.makeText(requireContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
