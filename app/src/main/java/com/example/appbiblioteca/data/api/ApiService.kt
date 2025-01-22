package com.example.appbiblioteca.data.api
import com.example.appbiblioteca.data.Categoria
import com.example.appbiblioteca.data.CreateLibroDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    // Endpoint para obtener todas las categorías
    @GET("categorias")
    fun getCategorias(): Call<List<Categoria>>


    @Multipart
    @POST("libros/create")
    suspend fun createLibro(
        @Part("isbn") isbn: RequestBody,
        @Part("tipo") tipo: RequestBody,
        @Part("titulo") titulo: RequestBody,
        @Part("areaDeConocimiento") areaDeConocimiento: RequestBody,
        @Part("autor") autor: RequestBody,
        @Part("numeroEjemplares") numeroEjemplares: RequestBody,
        @Part("codigoUbicacionFisica") codigoUbicacionFisica: RequestBody,
        @Part("verificacion") verificacion: RequestBody?,
        @Part("editorial") editorial: RequestBody,
        @Part("ano") ano: RequestBody,
        @Part("edicion") edicion: RequestBody,
        @Part imagenes: List<MultipartBody.Part>,
        @Part("categoriaId") categoriaId: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<CreateLibroDto>

    @GET("libros")
     fun getLibros():  Call<List<CreateLibroDto>>

        @Multipart
        @POST("libros/create")
        fun createLibro(
            @Part("libro") libro: RequestBody,
            @Part file: MultipartBody.Part?
        ): Call<CreateLibroDto>

}
