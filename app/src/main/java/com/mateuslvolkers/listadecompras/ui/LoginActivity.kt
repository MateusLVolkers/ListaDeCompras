package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.databinding.ActivityLoginBinding
import com.mateuslvolkers.listadecompras.extensions.toast
import com.mateuslvolkers.listadecompras.preferences.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val usuarioDao by lazy {
        val db = AppDatabase.instanciaDB(this)
        db.usuarioDao()
    }

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
//            Log.i("logando", "$usuario ---- $senha")
            autenticarUsuario(usuario, senha)
        }
    }

    private fun autenticarUsuario(usuario: String, senha: String) {
        lifecycleScope.launch {
            var autenticacao = false
            withContext(Dispatchers.IO) {
                usuarioDao.autenticaUsuario(usuario, senha)?.let { usuario ->
                    exibirMsgSucesso()
                    dataStore.edit { preferences ->
                        preferences[stringPreferencesKey("usuarioLogado")] = usuario.id
                    }
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    autenticacao = true
                    finish()
                }
            }
            if (!autenticacao) {
                toast("Erro ao autenticar usuário")
            }
        }
    }

    fun cadastrar() {
        binding.tvCadastrarUser.setOnClickListener {
            startActivity(Intent(this, CadastrarUsuario::class.java))
        }
    }

    fun exibirMsgSucesso() {
        lifecycleScope.launch {
            toast("Logando...")

        }
    }
}