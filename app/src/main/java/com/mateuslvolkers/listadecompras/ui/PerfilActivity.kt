package com.mateuslvolkers.listadecompras.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mateuslvolkers.listadecompras.databinding.ActivityPerfilBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PerfilActivity : UsuarioBaseActivity() {

    private val binding by lazy { ActivityPerfilBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configurarTexto()
        configurarBotaoSair()
    }

    private fun configurarTexto() {
        lifecycleScope.launch {
            usuario.filterNotNull().collect { usuarioLogado ->
                    binding.apply {
                        tvUsuario.text = usuarioLogado.id
                        tvNome.text = usuarioLogado.nome
                    }
                }
        }
    }

    private fun configurarBotaoSair() {
        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                deslogarUsuario()
            }
        }
    }
}