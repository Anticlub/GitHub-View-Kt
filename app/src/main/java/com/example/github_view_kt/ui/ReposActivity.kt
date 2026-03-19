package com.example.github_view_kt.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github_view_kt.R
import com.example.github_view_kt.adapter.RepoAdapter
import com.example.github_view_kt.model.Owner
import com.example.github_view_kt.model.Repo
import com.example.github_view_kt.network.RepoService
import com.squareup.picasso.Picasso
import java.net.URL


class ReposActivity : AppCompatActivity() {
    private lateinit var ivAvatar: ImageView
    private lateinit var tvName: TextView
    private val repoService = RepoService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_repos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvName = findViewById(R.id.tvNameOwner)
        ivAvatar = findViewById(R.id.ivAvatar)

        val recyclerView = findViewById<RecyclerView>(R.id.rvRepos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val json = intent.getStringExtra("reposJson")
        val repos = json?.let { repoService.parseRepos(it) }

        val firstOwner = repos?.firstOrNull()?.owner
        firstOwner?.let {
            tvName.text = it.login
            Picasso.get()
                .load(it.avatarURL)
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivAvatar)
        }
        val adapter = RepoAdapter(repos ?: emptyList())
        recyclerView.adapter = adapter

    }
}