package com.example.github_view_kt.network

import com.example.github_view_kt.model.Repo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class RepoService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(APIService::class.java)

    suspend fun fetchRepos(username: String): NetworkResult<List<Repo>> {
        return try {
            val response = service.getRepos(username)
            if (response.isSuccessful) {
                NetworkResult.Success(response.body() ?: emptyList())
            } else {
                NetworkResult.HttpError(response.code(), response.message())
            }
        } catch (e: IOException) {
            NetworkResult.NetworkError(e)
        }
    }
}