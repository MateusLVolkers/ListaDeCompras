package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import coil.load
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.databinding.ActivityDetalhesProdutoBinding
import com.mateuslvolkers.listadecompras.extensions.carregarImagem
import com.mateuslvolkers.listadecompras.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class DetalhesProduto : AppCompatActivity() {

    private val binding by lazy {ActivityDetalhesProdutoBinding.inflate(layoutInflater)}
    private lateinit var produtoCarregado: Produto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        carregarProduto()

    }

    fun configuraView(produto: Produto) {
        produtoCarregado = produto
//        Log.i("produtoCarregado", "${produtoCarregado}")

        binding.apply {
            titleDetalhe.text = produto.nome
            textDescricao.text = produto.descricao
            textPreco.text = conversorDeMoeda(produto.valor)
            imagemDetalhes.carregarImagem(produto.imagem)
        }
    }

    fun carregarProduto() {
        val bundle = intent.extras
        val produto = if (Build.VERSION.SDK_INT >= 33) {
            bundle?.getParcelable("produto", Produto::class.java)
        } else {
            bundle?.getParcelable("produto")
        }
//        Log.i("produtoclicado", "${produto}")

        if (produto != null) {
            configuraView(produto)
        } else {
            finish()
        }
    }

    fun conversorDeMoeda(valor: BigDecimal) : String  {
        val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        val numeroFormatado: String = formatador.format(valor)
        return numeroFormatado
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (::produtoCarregado.isInitialized){
            val db = AppDatabase.instanciaDB(this)
            val produtoDao = db.produtoDao()

            when(item.itemId){
                R.id.menu_detalhes_edicao -> {
                    Intent(this, FormularioCadastro::class.java).apply {
                        putExtra("produto", produtoCarregado)
                        startActivity(this)
                    }
                }
                R.id.menu_detalhes_remover -> {
                    produtoDao.deletarProduto(produtoCarregado)
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }



}