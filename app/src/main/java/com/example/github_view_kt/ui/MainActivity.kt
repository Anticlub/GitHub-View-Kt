package com.example.github_view_kt.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.github_view_kt.databinding.ActivityMainBinding
import com.example.github_view_kt.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSearchMain.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            if(username.isNotEmpty()){

                binding.progressBar.visibility = View.VISIBLE
                binding.btnSearchMain.isEnabled = false
                viewModel.getRepos(username)
            } else {
                Toast.makeText(this, "Introduce un usuario", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.repos.collect { repos ->
                        if (repos.isNotEmpty()){
                            viewModel.clearRepos()
                            binding.progressBar.visibility = View.GONE
                            binding.btnSearchMain.isEnabled = true
                            val intent = Intent(this@MainActivity, ReposActivity::class.java)
                            intent.putExtra("repos", ArrayList(repos))
                            startActivity(intent)
                        }
                    }
                }
                launch {
                    viewModel.error.collect { errorMsg ->
                        binding.progressBar.visibility = View.GONE
                        binding.btnSearchMain.isEnabled = true
                        Toast.makeText(this@MainActivity, errorMsg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}