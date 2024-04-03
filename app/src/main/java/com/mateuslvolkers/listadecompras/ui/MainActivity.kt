package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.databinding.ActivityMainBinding
import com.mateuslvolkers.listadecompras.model.Produto
import com.mateuslvolkers.listadecompras.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : UsuarioBaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val produtoDao by lazy {
        val db = AppDatabase.instanciaDB(this)
        db.produtoDao()
    }
    private val adapter = ListaProdutosAdapter(context = this)
    private val contextoCorrotinaIO: CoroutineDispatcher = Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
        verificaDataStoreUsuario()
        usuarioCarregarListaProdutos()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun usuarioCarregarListaProdutos() {
        lifecycleScope.launch {
            usuario.filterNotNull().collect {usuario ->
                    carregarListaPadrao(usuario.id)
            }
        }
    }

    private fun carregarListaPadrao(usuarioId: String) {
        lifecycleScope.launch {
            launch {
                buscarTodosProdutos(usuarioId).collect {
                    adapter.atualizar(it)
                }
            }
        }
    }

    private fun verificaDataStoreUsuario() {
        lifecycleScope.launch {
            verificaUsuarioLogado()
        }
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

        adapter.clicarEmRemover = { produto ->
            lifecycleScope.launch {
                withContext(contextoCorrotinaIO) {
                    produtoDao.deletarProduto(produto)
                }

                usuarioCarregarListaProdutos()

//                val produtosDB = buscarTodosProdutos()
//                produtosDB.collect {
//                    adapter.atualizar(it)
            }

//                val produtosRecebidosDB = withContext(contextoCorrotinaIO) {
//                    produtoDao.deletarProduto(produto)
//                    produtoDao.buscaTodos()
//                }
//                adapter.atualizar(produtosRecebidosDB)
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
        when (item.itemId) {
            R.id.menu_perfil -> {
                startActivity(Intent(this, PerfilActivity::class.java))
            }
            R.id.menu_logout -> {
                lifecycleScope.launch {
                    deslogarUsuario()
                }
            }
            R.id.menu_ordenar_nome_asc -> {
                lifecycleScope.launch {
                    buscarProdutosNomeAsc().let {
                        adapter.atualizar(it)
                    }
                }
            }
            R.id.menu_ordenar_nome_desc -> {
                lifecycleScope.launch {
                    buscarProdutosNomeDesc().let {
                        adapter.atualizar(it)
                    }
                }
            }
            R.id.menu_ordenar_preco_asc -> {
                lifecycleScope.launch {
                    buscarProdutosValorAsc().let {
                        adapter.atualizar(it)
                    }
                }
            }
            R.id.menu_ordenar_preco_desc -> {
                lifecycleScope.launch {
                    buscarProdutosValorDesc().let {
                        adapter.atualizar(it)
                    }
                }
            }
            R.id.menu_ordenar_padrao -> {
                lifecycleScope.launch {
                    usuarioCarregarListaProdutos()
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

    private suspend fun buscarTodosProdutos(usuarioId: String): Flow<List<Produto>> {
        val produtos = withContext(contextoCorrotinaIO) {
            produtoDao.buscaTodosProdutosUsuario(usuarioId)
        }
        return produtos
    }
}