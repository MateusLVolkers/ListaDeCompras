package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {ActivityLoginBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        login()
        cadastrar()
    }

    fun login() {
        binding.btnLogin.setOnClickListener {
            val usuario = binding.edtUsuario.text.toString()
            val senha = binding.edtSenha.text.toString()
            Log.i("logando", "$usuario ---- $senha")
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun cadastrar() {
        binding.tvCadastrarUser.setOnClickListener {
            startActivity(Intent(this, CadastrarUsuario::class.java))
        }
    }
}