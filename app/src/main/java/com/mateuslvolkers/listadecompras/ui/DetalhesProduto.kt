package com.mateuslvolkers.listadecompras.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.databinding.ActivityDetalhesProdutoBinding

class DetalhesProduto : AppCompatActivity() {

    private val binding by lazy {ActivityDetalhesProdutoBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}