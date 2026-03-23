package com.example.github_view_kt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.github_view_kt.R
import com.example.github_view_kt.model.Repo

class RepoAdapter (private val repoList: List<Repo>): RecyclerView.Adapter<RepoAdapter.RepoViewHolder>(){

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoName: TextView = itemView.findViewById(R.id.tvRepoName)
        val language: TextView = itemView.findViewById(R.id.tvRepoLanguage)
    }
    override fun onCreateViewHolder(
        view: ViewGroup,
        viewId: Int
    ): RepoViewHolder {
        val v = LayoutInflater.from(view.context)
            .inflate(R.layout.item_repo, view, false)
        return RepoViewHolder(v)
    }

    override fun onBindViewHolder(
        view: RepoViewHolder,
        viewId: Int
    ) {
        val repo = repoList[viewId]
        view.repoName.text = repo.name
        view.language.text = repo.language ?: "Sin lenguaje"
    }

    override fun getItemCount(): Int = repoList.size


}