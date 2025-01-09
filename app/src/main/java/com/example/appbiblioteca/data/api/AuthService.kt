package com.example.appbiblioteca.data.api


import com.example.appbiblioteca.response.LoginResponse
import com.example.appbiblioteca.data.models.LoginRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("auth/login")
    fun postLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    companion object {
        private const val BASE_URL = "http://192.168.100.30:3000/"

        fun create(): AuthService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(AuthService::class.java)
        }
    }
}
