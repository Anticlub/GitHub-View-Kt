package com.example.github_view_kt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.github_view_kt.R
import com.example.github_view_kt.model.Repo
import com.example.github_view_kt.ui.ReposActivity

class RepoAdapter (private val repoList: List<Repo>): RecyclerView.Adapter<RepoAdapter.RepoViewHolder>(){

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val repoName: TextView = itemView.findViewById(R.id.tvRepoName)
        val language: TextView = itemView.findViewById(R.id.tvRepoLanguage)
    }
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): RepoViewHolder {
        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.item_repo, p0, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(
        p0: RepoViewHolder,
        p1: Int
    ) {
        val repo = repoList[p1]
        p0.repoName.text = repo.name
        p0.language.text = repo.language ?: "Sin lenguaje"
    }

    override fun getItemCount(): Int = repoList.size


}