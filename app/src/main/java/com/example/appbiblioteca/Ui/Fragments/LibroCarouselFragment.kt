package com.example.appbiblioteca.Ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appbiblioteca.R
import com.example.appbiblioteca.adaptadores.LibrosAdapter
import com.example.appbiblioteca.data.Libros
import com.example.appbiblioteca.data.api.ApiService
import com.example.appbiblioteca.databinding.FragmentLibroCarouselBinding
import com.example.appbiblioteca.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LibroCarouselFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var libroAdapter: LibrosAdapter
    private val libroList = mutableListOf<Libros>()
    private val apiService: ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLibroCarouselBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerViewLibros
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) // Establece el RecyclerView como un carrusel horizontal
        libroAdapter = LibrosAdapter(libroList)
        recyclerView.adapter = libroAdapter

        obtenerLibros()

        return binding.root
    }

    private fun obtenerLibros() {
        // Realizar la solicitud para obtener los libros
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.getLibros()
                if (response.isSuccessful && response.body() != null) {
                    libroList.clear()
                    libroList.addAll(response.body()!!)
                    libroAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "Error al obtener los libros", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
