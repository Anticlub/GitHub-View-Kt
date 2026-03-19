package com.example.github_view_kt.network

import com.example.github_view_kt.model.Repo
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException


class RepoService {

    private val client = OkHttpClient()
    private val moshi = Moshi.Builder()
        .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
        .build()

    fun fetchReposJson(user: String): NetworkResult<String> {

        val url = "https://api.github.com/users/$user/repos"
        val request = Request.Builder().url(url).build()

        val response = try {
            client.newCall(request).execute()
        } catch (e: IOException) {
            return NetworkResult.NetworkError(e)
        }

        response.use {
            if(!response.isSuccessful) {
                return NetworkResult.HttpError(
                    code = response.code(),
                    message = response.message()
                )
            }

            val body = response.body()?.string()
                ?: return NetworkResult.HttpError(response.code(), "Cuerpo vacío")

            return NetworkResult.Success(body)
        }
    }

    fun parseRepos(jsonString: String): List<Repo>? {
        val type = Types.newParameterizedType(List::class.java, Repo::class.java)
        val adapter = moshi.adapter<List<Repo>>(type)
        return adapter.fromJson(jsonString)
    }
}