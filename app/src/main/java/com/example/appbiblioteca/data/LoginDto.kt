package com.example.appbiblioteca.data

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("nombreUsuario") val email: String,
    @SerializedName("password") val password: String
)
