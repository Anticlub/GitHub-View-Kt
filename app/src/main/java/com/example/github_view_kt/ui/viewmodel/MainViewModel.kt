package com.example.github_view_kt.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_view_kt.model.Repo
import com.example.github_view_kt.network.NetworkResult
import com.example.github_view_kt.network.RepoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val repoService = RepoService()
    private val _repos = MutableStateFlow<List<Repo>>(emptyList())
    val repos: StateFlow<List<Repo>> = _repos

    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    fun getRepos(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repoService.fetchRepos(username)) {
                is NetworkResult.Success -> {
                    _repos.value = result.data
                }
                is NetworkResult.HttpError -> {
                    val msg = when (result.code) {
                        404 -> "Usuario no encontrado"
                        403 -> "Limite de API alcanzado"
                        else -> "Error del servidor: ${result.code}"
                    }
                    _error.emit(msg)
                }
                is NetworkResult.NetworkError -> {
                    _error.emit("Sin conexión a internet")
                }
                is NetworkResult.ParseError -> {
                    _error.emit("Error al procesar la respuesta")
                }
            }
        }
    }

    fun clearRepos() {
        _repos.value = emptyList()
    }
}