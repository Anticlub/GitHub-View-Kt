package com.example.github_view_kt.network

import java.io.IOException

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class HttpError(val code: Int, val message: String) : NetworkResult<Nothing>()
    data class NetworkError(val exception: IOException) : NetworkResult<Nothing>()
    data class ParseError(val exception: Exception) : NetworkResult<Nothing>()
}