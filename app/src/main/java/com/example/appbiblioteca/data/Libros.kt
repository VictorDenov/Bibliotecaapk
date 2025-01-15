package com.example.appbiblioteca.data

data class Libros(
    val id: Int,
    val isbn: String,
    val tipo: String,
    val titulo: String,
    val areaDeConocimiento: String,
    val autor: String,
    val numeroEjemplares: Int,
    val codigoUbicacionFisica: String,
    val verificacion: String?,
    val editorial: String,
    val ano: Int,
    val edicion: String,
    val categoriaId: Int,
    val imagenes: List<String> // Suponiendo que es una lista de URLs de imágenes
)
