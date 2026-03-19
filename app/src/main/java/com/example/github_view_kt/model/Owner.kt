package com.example.github_view_kt.model

import com.squareup.moshi.Json


data class Owner(
    val login: String,
    //val name: String,
    @Json(name = "avatar_url") val avatarURL: String
)
