package com.example.github_view_kt.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.github_view_kt.R
import com.example.github_view_kt.network.NetworkResult
import com.example.github_view_kt.network.RepoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var btnSearch: Button
    val repoService = RepoService()

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
                CoroutineScope(Dispatchers.IO).launch {
                    when (val result = repoService.fetchReposJson(username)) {
                        is NetworkResult.Success -> {
                            launch(Dispatchers.Main) {
                                val intent = Intent(this@MainActivity, ReposActivity::class.java)
                                intent.putExtra("reposJson", result.data)
                                startActivity(intent)
                            }
                        }
                        is NetworkResult.HttpError -> {
                            launch(Dispatchers.Main) {
                                val msg = when (result.code) {
                                    404 -> "Usuario no encontrado"
                                    403 -> "Límite de API alcanzado"
                                    else -> "Error del servidor: ${result.code}"
                                }
                                Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                        is NetworkResult.NetworkError -> {
                            launch(Dispatchers.Main) {
                                Toast.makeText(this@MainActivity, "Sin conexión a internet", Toast.LENGTH_SHORT).show()
                            }
                        }
                        is NetworkResult.ParseError -> {
                            launch(Dispatchers.Main) {
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