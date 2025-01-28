package com.example.appbiblioteca.data.models

data class LoginBibliotecarioRequest(
    val nombreBibliotecario: String,
    val password: String
)

data class TokenResponse(
    val token: String
)
