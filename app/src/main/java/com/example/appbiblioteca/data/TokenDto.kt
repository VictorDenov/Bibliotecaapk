package com.example.appbiblioteca.data

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("accessToken") val accessTokenVerify: String

)
