package com.example.github_view_kt.network

import com.example.github_view_kt.model.Repo
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.Request


class RepoService {

    private val client = OkHttpClient()

    fun fetchReposJson(user: String): String? {
        val url = "https://api.github.com/users/$user/repos"
        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                println("Error: ${response.code()}")
                return null
            }
            return response.body()?.string()
        }
    }

    fun parseRepos(jsonString: String): List<Repo>? {
        val moshi = Moshi.Builder().add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(List::class.java, Repo::class.java)
        val adapter = moshi.adapter<List<Repo>>(type)
        return adapter.fromJson(jsonString)
    }
}