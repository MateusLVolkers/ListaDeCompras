package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.databinding.ActivityMainBinding
import com.mateuslvolkers.listadecompras.ui.recyclerview.adapter.ListaProdutosAdapter

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        AppDatabase.instanciaDB(this).produtoDao()
    }
    private val adapter = ListaProdutosAdapter(context = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        // .allowMainThreadQueries() não é uma boa prática
        adapter.atualizar(produtoDao.buscaTodos())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ordenar_produtos, menu)
        return super.onCreateOptionsMenu(menu)
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
        recyclerview.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        adapter.click = {
            val intent = Intent(this, DetalhesProduto::class.java)
            intent.putExtra("produtoID", it.id)
            startActivity(intent)
        }

        adapter.clicarEmRemover = {produto ->
            produtoDao.deletarProduto(produto)
            adapter.atualizar(produtoDao.buscaTodos())
        }
        adapter.clicarEmEditar = {
            Intent(this, FormularioCadastro::class.java).apply {
                putExtra("produtoID", it.id)
                startActivity(this)
            }
        }
    }
}

