package com.example.appbiblioteca.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://192.168.100.30:3000/" // Cambia esta URL a la que uses en tu backend

    // Crear una instancia de Retrofit
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)  // Base URL de tu API
            .addConverterFactory(GsonConverterFactory.create())  // Para convertir respuestas JSON a objetos Kotlin
            .build()
    }
}