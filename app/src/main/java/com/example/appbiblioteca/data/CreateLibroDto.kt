package com.example.appbiblioteca.data

import android.os.Parcelable
import kotlinx.serialization.Serializable


import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class CreateLibroDto(
    val isbn: String, // ISBN único del libro
    val tipo: String, // Tipo de libro (físico, digital)
    val titulo: String,
    val areaDeConocimiento: String,
    val autor: String,
    val numeroEjemplares: Int,
    val codigoUbicacionFisica: String,
    val verificacion: String? = null, // Verificación (opcional)
    val editorial: String,
    val ano: Int,
    val edicion: String,
    val imagenes: @RawValue List<CreateImagenLibroDto>, // Imagenes, si están disponibles
    val categoriaId: Int // ID de la categoría relacionada
): Parcelable
