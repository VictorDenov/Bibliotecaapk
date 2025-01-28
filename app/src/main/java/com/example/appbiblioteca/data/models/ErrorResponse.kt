package com.example.appbiblioteca.data.models

data class ErrorResponse(
    val status: String,
    val message: String,
    val code: Int
)
