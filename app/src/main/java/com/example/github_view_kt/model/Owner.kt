package com.example.github_view_kt.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Owner(
    val login: String,
    @SerializedName("avatar_url") val avatarURL: String
) : Serializable
