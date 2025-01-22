package com.example.appbiblioteca.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class CreateImagenLibroDto(
    val public_id: String,
    val url: String
) :Parcelable