package com.example.github_view_kt.network

import com.example.github_view_kt.model.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String) : Response<List<Repo>>
}