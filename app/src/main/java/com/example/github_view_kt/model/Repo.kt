package com.example.github_view_kt.model

import java.net.URL

data class Repo(
    val name: String,
    val owner: Owner,
    val language: String?
)
