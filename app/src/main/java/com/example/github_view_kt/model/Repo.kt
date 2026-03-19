package com.example.github_view_kt.model

import java.io.Serializable
import java.net.URL

data class Repo(
    val name: String,
    val owner: Owner,
    val language: String?
) : Serializable
