package com.mateuslvolkers.listadecompras.ui.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.mateuslvolkers.listadecompras.databinding.DialogFormularioImagemBinding
import com.mateuslvolkers.listadecompras.extensions.carregarImagem

class FormularioDialog(private val context: Context) {

    fun show(
        urlPadrao: String? = null, confirmarImagem: (imagem: String) -> Unit
    ) {
        val binding = DialogFormularioImagemBinding.inflate(LayoutInflater.from(context))

        urlPadrao?.let {
            binding.imgDialog.carregarImagem(urlPadrao)
        }

        binding.btnCarregarImagem.setOnClickListener {
            val url = binding.edtUrl.text.toString()
            binding.imgDialog.carregarImagem(url)
        }
        AlertDialog.Builder(context).setView(binding.root).setPositiveButton("Confirmar") { _, _ ->
            val url = binding.edtUrl.text.toString()
            confirmarImagem(url)
        }.setNegativeButton("Cancelar") { _, _ -> }.show()
    }

}