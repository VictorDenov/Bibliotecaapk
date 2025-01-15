package com.example.appbiblioteca.data

data class Categoria(
    val id: Int,
    val nombre: String,
    val imagenes: List<ImagenCategoria> // Lista de imágenes asociadas
)

data class ImagenCategoria(
    val nombre: String,
    val url: String
)
