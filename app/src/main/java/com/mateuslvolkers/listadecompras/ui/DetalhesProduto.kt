package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.databinding.ActivityDetalhesProdutoBinding
import com.mateuslvolkers.listadecompras.extensions.carregarImagem
import com.mateuslvolkers.listadecompras.model.Produto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class DetalhesProduto : AppCompatActivity() {

    private val binding by lazy { ActivityDetalhesProdutoBinding.inflate(layoutInflater) }
    private var produtoCarregado: Produto? = null
    private var produtoId: Long = 0L
    private val produtoDao by lazy { AppDatabase.instanciaDB(this).produtoDao() }
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        carregarProduto()
    }

    override fun onResume() {
        super.onResume()
        carregarProdutoBanco()
    }

    private fun carregarProdutoBanco() {
        scope.launch {
            produtoCarregado = produtoDao.buscarPorId(produtoId)
            withContext(Main){
                produtoCarregado?.let {
                    configuraView(it)
                } ?: finish()
            }
        }
    }

    private fun configuraView(produto: Produto) {
        binding.apply {
            titleDetalhe.text = produto.nome
            textDescricao.text = produto.descricao
            textPreco.text = conversorDeMoeda(produto.valor)
            imagemDetalhes.carregarImagem(produto.imagem)
        }
    }

    private fun carregarProduto() {
        produtoId = intent.getLongExtra("produtoID", 0L)

//        Implementação anterior - Refatorado
//        val bundle = intent.extras
//        val produto = if (Build.VERSION.SDK_INT >= 33) {
//            bundle?.getParcelable("produto", Produto::class.java)
//        } else {
//            bundle?.getParcelable("produto")
//        }
//        Log.i("produtoclicado", "${produto}")
//        if (produto != null) {
//            produtoId = produto.id
////        Log.i("produtoCarregado", "${produtoCarregado}")
//            configuraView(produto)
//        } else {
//            finish()
//        }
    }

    private fun conversorDeMoeda(valor: BigDecimal): String {
        val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        val numeroFormatado: String = formatador.format(valor)
        return numeroFormatado
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_edicao -> {
                Intent(this, FormularioCadastro::class.java).apply {
                    putExtra("produtoID", produtoId)
                    startActivity(this)
                }
            }

            R.id.menu_detalhes_remover -> {
                scope.launch {
                    produtoCarregado?.let {
                        produtoDao.deletarProduto(it)
                        finish()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}