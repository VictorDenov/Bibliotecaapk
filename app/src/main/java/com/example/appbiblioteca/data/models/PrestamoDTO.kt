package com.example.appbiblioteca.data.models

data class PrestamoDTO(
    val libro_id: Int,
    val estudiante_id: Int,
    val bibliotecario_id: Int,
    val fechaPrestamo: String,
    val fechaDevolucion: String,
    val fechaRealDevolucion: String?,
    val estaDevuelto: Boolean
)
