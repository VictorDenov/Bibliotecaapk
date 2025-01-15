package com.example.appbiblioteca.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Base URL de tu API
    private const val BASE_URL = "http://192.168.100.30:3000/"  // Reemplázalo con la URL correcta

    private var retrofit: Retrofit? = null

    // Método para obtener la instancia de Retrofit
    fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())  // Para convertir las respuestas JSON a objetos Kotlin
                .build()
        }
        return retrofit!!
    }
}
