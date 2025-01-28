package com.example.appbiblioteca.data.api

import com.example.appbiblioteca.data.models.LoginBibliotecarioRequest
import com.example.appbiblioteca.data.models.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login/bibliotecario")
    fun loginBibliotecario(@Body request: LoginBibliotecarioRequest): Call<TokenResponse>
}
