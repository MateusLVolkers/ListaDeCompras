package com.mateuslvolkers.listadecompras.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.databinding.ActivityCadastrarUsuarioBinding
import com.mateuslvolkers.listadecompras.extensions.toast
import com.mateuslvolkers.listadecompras.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CadastrarUsuario : AppCompatActivity() {

    private val binding by lazy { ActivityCadastrarUsuarioBinding.inflate(layoutInflater) }
    private val usuarioDao by lazy {
        val db = AppDatabase.instanciaDB(this)
        db.usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configurarBtnCadastro()
    }

    fun configurarBtnCadastro() {
        binding.btnCadastrar.setOnClickListener {
            cadastrarUsuario()
        }
    }

    fun cadastrarUsuario() {
        val novoUsuario = criaUsuario()
        cadastrarNovoUsuario(novoUsuario)
    }

    private fun cadastrarNovoUsuario(novoUsuario: Usuario) {
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    usuarioDao.salvar(novoUsuario)
                    finish()
                }
            } catch (e: Exception) {
                Log.i("CadastrarUsuario", "Erro:", e)
                toast("Erro ao cadastar")
            }
        }
    }

    fun criaUsuario(): Usuario {
        val usuario = binding.edtUsuario.text.toString()
        val nome = binding.edtNome.text.toString()
        val senha = binding.edtSenha.text.toString()
        return Usuario(usuario,nome,senha)
    }


}