package com.example.appbiblioteca.data.api
import com.example.appbiblioteca.data.Categoria
import com.example.appbiblioteca.data.Libros
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // Endpoint para obtener todas las categorías
    @GET("categorias")
    fun getCategorias(): Call<List<Categoria>>

    @GET("libros")
    suspend fun getLibros(): Response<List<Libros>>

    // Endpoint para obtener libros de una categoría específica
    @GET("libros/categoria/{id}")
    fun getLibrosByCategoria(@Path("id") categoriaId: Int): Call<List<Libros>>
}
