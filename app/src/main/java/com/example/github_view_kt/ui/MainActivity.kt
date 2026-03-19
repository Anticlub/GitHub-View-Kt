package com.example.github_view_kt.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.github_view_kt.R
import com.example.github_view_kt.model.Repo
import com.example.github_view_kt.network.RepoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var btnSearch: Button
private val repoService = RepoService()
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSearch = findViewById(R.id.btnSearchMain)

        btnSearch.setOnClickListener {
            val username = findViewById<EditText>(R.id.etUsername).text.toString().trim()
            if(username.isNotEmpty()){
                Toast.makeText(this, "Usuario: $username", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ReposActivity::class.java)
                CoroutineScope(Dispatchers.IO).launch {
                    val json = repoService.fetchReposJson(username)
                    val repos = json?.let { repoService.parseRepos(it) }
                    repos?.forEach {
                        Log.d("REPO","lang: ${it.language}, login: ${it.owner.login}, nombrerepo: ${it.name}, avatar: ${it.owner.avatarURL}")
                    }
                }
                //startActivity(intent)
            } else {
                Toast.makeText(this, "Introduce un usario", Toast.LENGTH_SHORT).show()
            }
        }
    }
}