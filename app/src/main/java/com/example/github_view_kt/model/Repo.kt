package com.example.github_view_kt.model

import java.io.Serializable


data class Repo(
    val name: String,
    val owner: Owner,
    val language: String?
) : Serializable
