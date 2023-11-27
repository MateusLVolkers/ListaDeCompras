package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.model.Produto
import com.mateuslvolkers.listadecompras.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.adapter = ListaProdutosAdapter(context = this, produtos = listOf(
            Produto(nome = "produto 1",
                    descricao = "Descrição do produto 1",
                    valor = BigDecimal("19.99")
            ),
            Produto(nome = "mange",
                    descricao = "verde",
                    valor = BigDecimal("15.00")
            ),
            Produto(nome = "teste",
                descricao = "testando",
                valor = BigDecimal("1.00")
            ),
        ))

        val fab = findViewById<FloatingActionButton>(R.id.fab_adicionar)
        fab.setOnClickListener {
            val intent = Intent(this, FormularioCadastro::class.java)
            startActivity(intent)
        }
    }
}