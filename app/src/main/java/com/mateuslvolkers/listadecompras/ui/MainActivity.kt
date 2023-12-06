package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.dao.ProdutosDao
import com.mateuslvolkers.listadecompras.databinding.ActivityMainBinding
import com.mateuslvolkers.listadecompras.ui.recyclerview.adapter.ListaProdutosAdapter

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val dao = ProdutosDao()
    private val adapter = ListaProdutosAdapter(context = this, produtos = dao.buscarTodos()){ produto ->
        val intent = Intent(this, DetalhesProduto::class.java)
        intent.putExtra("produto", produto)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualizar(dao.buscarTodos())
    }

    fun configuraFab() {
        val fab = binding.fabAdicionar
        fab.setOnClickListener {
            val intent = Intent(this, FormularioCadastro::class.java)
            startActivity(intent)
        }
    }

    fun configuraRecyclerView() {
        val recyclerview = binding.recyclerview
        recyclerview.adapter = adapter
//        recyclerview.addItemDecoration(DividerItemDecoration(this,RecyclerView.VERTICAL))
    }

}

