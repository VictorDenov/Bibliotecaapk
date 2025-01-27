package com.example.appbiblioteca.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Categoria(
    val id: Int,
    val nombre: String,
    val imagenes: List<ImagenCategoria> // Lista de imágenes asociadas
) : Parcelable

@Parcelize
data class ImagenCategoria(
    val nombre: String,
    val url: String
) : Parcelable
