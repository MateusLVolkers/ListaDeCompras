package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.databinding.ActivityMainBinding
import com.mateuslvolkers.listadecompras.ui.recyclerview.adapter.ListaProdutosAdapter

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
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
        val produtoDaoDB = AppDatabase.instanciaDB(this).produtoDao()
        adapter.atualizar(produtoDaoDB.buscaTodos())
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
        adapter.click = {
            val intent = Intent(this, DetalhesProduto::class.java)
            intent.putExtra("produto", it)
            startActivity(intent)
        }

        adapter.clicarEmRemover = {
            Log.i("cliques", "Remover $it")
        }
        adapter.clicarEmEditar = {
            Log.i("cliques", "Editar $it")
        }


    }
}

