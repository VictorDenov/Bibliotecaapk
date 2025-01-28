package com.example.appbiblioteca.interceptores
import okhttp3.Interceptor
import okhttp3.Response
import android.content.SharedPreferences

class AuthInterceptor(private val sharedPreferences: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Recuperamos el token de SharedPreferences
        val token = sharedPreferences.getString("authToken", null)

        // Si el token no es nulo, lo añadimos al encabezado de la solicitud
        val requestBuilder = chain.request().newBuilder()
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        // Continuamos con la solicitud
        return chain.proceed(requestBuilder.build())
    }
}
