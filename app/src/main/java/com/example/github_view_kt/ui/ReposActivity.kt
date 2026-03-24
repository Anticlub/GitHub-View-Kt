package com.example.github_view_kt.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_view_kt.R
import com.example.github_view_kt.adapter.RepoAdapter
import com.example.github_view_kt.databinding.ActivityReposBinding
import com.example.github_view_kt.model.Repo
import com.example.github_view_kt.network.RepoService
import com.example.github_view_kt.ui.viewmodel.ReposViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.util.ArrayList


class ReposActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReposBinding
    private val viewModel: ReposViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReposBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvRepos.layoutManager = LinearLayoutManager(this)
        binding.rvRepos.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.repos.collect { repos ->
                    if (repos.isNotEmpty()) {
                        val firstOwner = repos.first().owner
                        binding.tvNameOwner.text = firstOwner.login
                        Picasso.get()
                            .load(firstOwner.avatarURL)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(binding.ivAvatar)

                        binding.rvRepos.adapter = RepoAdapter(repos)
                    }
                }
            }
        }

    }
}