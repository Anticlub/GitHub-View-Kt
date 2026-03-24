package com.example.github_view_kt.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.github_view_kt.model.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReposViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _repos = MutableStateFlow<List<Repo>>(emptyList())
    val repos: StateFlow<List<Repo>> = _repos

    init {
        val repos = savedStateHandle.get<ArrayList<Repo>>("repos")
        _repos.value = repos ?: emptyList()
    }
}