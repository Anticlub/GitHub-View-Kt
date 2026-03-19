package com.example.github_view_kt.model

import android.os.Parcelable
import com.squareup.moshi.Json
import java.io.Serializable

data class Owner(
    val login: String,
    @Json(name = "avatar_url") val avatarURL: String
) : Serializable
