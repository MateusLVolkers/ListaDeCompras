package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.databinding.ActivityMainBinding
import com.mateuslvolkers.listadecompras.model.Produto
import com.mateuslvolkers.listadecompras.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val produtoDao by lazy { AppDatabase.instanciaDB(this).produtoDao() }
    private val adapter = ListaProdutosAdapter(context = this)
    private val scope: CoroutineScope = MainScope()
    private lateinit var produtoRecebido: List<Produto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        scope.launch {
            val produtosDB = withContext(Dispatchers.IO) {
                produtoDao.buscaTodos()
            }
            adapter.atualizar(produtosDB)
        }
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
//        recyclerview.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        adapter.click = {
            val intent = Intent(this, DetalhesProduto::class.java)
            intent.putExtra("produtoID", it.id)
            startActivity(intent)
        }

        adapter.clicarEmRemover = {produto ->
            scope.launch {
                val produtosRecebidosDB = withContext(Dispatchers.IO) {
                    produtoDao.deletarProduto(produto)
                    produtoDao.buscaTodos()
                }
                adapter.atualizar(produtosRecebidosDB)
            }
        }
        adapter.clicarEmEditar = {
            Intent(this, FormularioCadastro::class.java).apply {
                putExtra("produtoID", it.id)
                startActivity(this)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ordenar_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_ordenar_nome_asc -> {
                scope.launch {
                    produtoRecebido = withContext(Dispatchers.IO) {
                        produtoDao.buscarNomeAsc()
                    }
                    produtoRecebido.let {
                        adapter.atualizar(it)
                    }
                }
            }
            R.id.menu_ordenar_nome_desc -> {
                scope.launch {
                    produtoRecebido = withContext(Dispatchers.IO) {
                        produtoDao.buscarNomeDesc()
                    }
                    produtoRecebido.let {
                        adapter.atualizar(it)
                    }
                }
            }
            R.id.menu_ordenar_preco_asc -> {
                scope.launch {
                    produtoRecebido = withContext(Dispatchers.IO) {
                        produtoDao.buscarValorAsc()
                    }
                    produtoRecebido.let {
                        adapter.atualizar(it)
                    }
                }
            }
            R.id.menu_ordenar_preco_desc -> {
                scope.launch {
                    produtoRecebido = withContext(Dispatchers.IO) {
                        produtoDao.buscarValorDesc()
                    }
                    produtoRecebido.let {
                        adapter.atualizar(it)
                    }
                }
            }
            R.id.menu_ordenar_padrao -> {
                scope.launch {
                    produtoRecebido = withContext(Dispatchers.IO) {
                        produtoDao.buscaTodos()
                    }
                    produtoRecebido.let {
                        adapter.atualizar(it)
                    }
                }
            }
            else -> null
        }
//        Log.i("listaOrdenadad", "$produtoReordenado")
//        produtoReordenado?.let{
//            adapter.atualizar(it)
//        }
        return super.onOptionsItemSelected(item)
    }
}