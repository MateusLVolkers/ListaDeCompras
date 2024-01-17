package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.databinding.ActivityMainBinding
import com.mateuslvolkers.listadecompras.model.Produto
import com.mateuslvolkers.listadecompras.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val produtoDao by lazy { AppDatabase.instanciaDB(this).produtoDao() }
    private val adapter = ListaProdutosAdapter(context = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        val scope = MainScope()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ordenar_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val produtoReordenado: List<Produto>? = when(item.itemId){
            R.id.menu_ordenar_nome_asc -> {
                produtoDao.buscarNomeAsc()
            }
            R.id.menu_ordenar_nome_desc -> {
                produtoDao.buscarNomeDesc()
            }
            R.id.menu_ordenar_preco_asc -> {
                produtoDao.buscarValorAsc()
            }
            R.id.menu_ordenar_preco_desc -> {
                produtoDao.buscarValorDesc()
            }
            R.id.menu_ordenar_padrao -> {
                produtoDao.buscaTodos()
            }
            else -> null
        }
//        Log.i("listaOrdenadad", "$produtoReordenado")

        produtoReordenado?.let{
            adapter.atualizar(it)
        }
        return super.onOptionsItemSelected(item)
    }
}