package com.example.github_view_kt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github_view_kt.databinding.ItemRepoBinding
import com.example.github_view_kt.model.Repo

class RepoAdapter (private val repoList: List<Repo>): RecyclerView.Adapter<RepoAdapter.RepoViewHolder>(){

    class RepoViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            binding.tvRepoName.text = repo.name.replaceFirstChar { it.uppercase() }
            binding.tvRepoLanguage.text = repo.language ?: "Sin lenguaje"
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RepoViewHolder,
        position: Int
    ) {
        holder.bind(repoList[position])
    }

    override fun getItemCount(): Int = repoList.size
}