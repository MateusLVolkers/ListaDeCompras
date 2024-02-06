package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.databinding.ActivityMainBinding
import com.mateuslvolkers.listadecompras.model.Produto
import com.mateuslvolkers.listadecompras.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.CoroutineDispatcher
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
    private val contextoCorrotinaIO: CoroutineDispatcher = Dispatchers.IO
    private lateinit var produtoRecebido: List<Produto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val produtosDB = buscarTodosProdutos()
            adapter.atualizar(produtosDB)
        }
//        scope.launch {
//            val produtosDB = buscarTodosProdutos()
//            adapter.atualizar(produtosDB)
//        }
    }

    private fun configuraFab() {
        val fab = binding.fabAdicionar
        fab.setOnClickListener {
            val intent = Intent(this, FormularioCadastro::class.java)
            startActivity(intent)
        }
    }

    private fun configuraRecyclerView() {
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
                val produtosRecebidosDB = withContext(contextoCorrotinaIO) {
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
                    produtoRecebido = buscarProdutosNomeAsc()
                    produtoRecebido?.let {
                        adapter.atualizar(it)
                    }
                }
            }
            R.id.menu_ordenar_nome_desc -> {
                scope.launch {
                    produtoRecebido = buscarProdutosNomeDesc()
                    produtoRecebido?.let {
                        adapter.atualizar(it)
                    }
                }
            }
            R.id.menu_ordenar_preco_asc -> {
                scope.launch {
                    produtoRecebido = buscarProdutosValorAsc()
                    produtoRecebido?.let {
                        adapter.atualizar(it)
                    }
                }
            }
            R.id.menu_ordenar_preco_desc -> {
                scope.launch {
                    produtoRecebido = buscarProdutosValorDesc()
                    produtoRecebido?.let {
                        adapter.atualizar(it)
                    }
                }
            }
            R.id.menu_ordenar_padrao -> {
                scope.launch {
                    produtoRecebido = buscarTodosProdutos()
                    produtoRecebido?.let {
                        adapter.atualizar(it)
                    }
                }
            }
        }
//        Log.i("listaOrdenadad", "$produtoReordenado")
//        produtoReordenado?.let{
//            adapter.atualizar(it)
//        }
        return super.onOptionsItemSelected(item)
    }

    private suspend fun buscarProdutosNomeDesc(): List<Produto> {
        val produtos = withContext(contextoCorrotinaIO) {
            produtoDao.buscarNomeDesc()
        }
        return produtos
    }
    private suspend fun buscarProdutosNomeAsc(): List<Produto> {
        val produtos = withContext(contextoCorrotinaIO) {
            produtoDao.buscarNomeAsc()
        }
        return produtos
    }
    private suspend fun buscarProdutosValorDesc(): List<Produto> {
        val produtos = withContext(contextoCorrotinaIO) {
            produtoDao.buscarValorDesc()
        }
        return produtos
    }
    private suspend fun buscarProdutosValorAsc(): List<Produto> {
        val produtos = withContext(contextoCorrotinaIO) {
            produtoDao.buscarValorAsc()
        }
        return produtos
    }
    private suspend fun buscarTodosProdutos(): List<Produto> {
        val produtos = withContext(contextoCorrotinaIO) {
            produtoDao.buscaTodos()
        }
        return produtos
    }
}