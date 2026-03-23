package com.example.github_view_kt.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.github_view_kt.R
import com.example.github_view_kt.databinding.ActivityMainBinding
import com.example.github_view_kt.network.NetworkResult
import com.example.github_view_kt.network.RepoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val repoService = RepoService()

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

                CoroutineScope(Dispatchers.IO).launch {
                    val result = repoService.fetchRepos(username)
                    launch(Dispatchers.Main) {
                        binding.progressBar.visibility = View.GONE
                        binding.btnSearchMain.isEnabled = true

                        when (result) {
                            is NetworkResult.Success -> {
                                val intent = Intent(this@MainActivity, ReposActivity::class.java)
                                intent.putExtra("repos", ArrayList(result.data))
                                startActivity(intent)
                            }
                            is NetworkResult.HttpError -> {
                                val msg = when (result.code) {
                                    404 -> "Usuario no encontrado"
                                    403 -> "Límite de API alcanzado"
                                    else -> "Error del servidor: ${result.code}"
                                }
                                Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                            }
                            is NetworkResult.NetworkError -> {
                                Toast.makeText(this@MainActivity, "Sin conexión a internet", Toast.LENGTH_SHORT).show()
                            }
                            is NetworkResult.ParseError -> {
                                Toast.makeText(this@MainActivity, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Introduce un usuario", Toast.LENGTH_SHORT).show()
            }
        }

    }
}