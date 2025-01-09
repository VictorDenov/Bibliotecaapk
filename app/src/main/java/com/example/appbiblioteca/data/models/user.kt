package com.example.appbiblioteca.data.models

import java.util.Date

data class user(
    val id: Int,
    val nombre: String,
    val nombreUsuario: String,
    val email: String,
    val password: String, // Aunque esté encriptado, se puede dejar como String
    val activo: Boolean,
    val fechaCreacion: Date,
    val fechaUltimoAcceso: Date?,
    val titulo: String?,
    val fechaNacimiento: Date?,
    val direccion: String?,
    val telefono: String?,
    val fechaIngreso: Date?,
    val especialidad: String?,
    val fotoPerfil: String?,
    val roles: List<String> // O bien un objeto si `roles` tiene más propiedades
)