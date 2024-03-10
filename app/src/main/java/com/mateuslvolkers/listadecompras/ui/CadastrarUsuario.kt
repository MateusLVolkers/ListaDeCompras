package com.mateuslvolkers.listadecompras.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.databinding.ActivityCadastrarUsuarioBinding
import com.mateuslvolkers.listadecompras.model.Usuario

class CadastrarUsuario : AppCompatActivity() {

    private val binding by lazy { ActivityCadastrarUsuarioBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    fun criaUsuario(): Usuario {
        val usuario = binding.edtUsuario.text.toString()
        val nome = binding.edtNome.text.toString()
        val senha = binding.edtSenha.text.toString()
        return Usuario(usuario,nome,senha)
    }


}