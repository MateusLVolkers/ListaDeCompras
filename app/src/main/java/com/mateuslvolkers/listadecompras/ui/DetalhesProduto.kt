package com.mateuslvolkers.listadecompras.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import coil.load
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.databinding.ActivityDetalhesProdutoBinding
import com.mateuslvolkers.listadecompras.extensions.carregarImagem
import com.mateuslvolkers.listadecompras.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class DetalhesProduto : AppCompatActivity() {

    private val binding by lazy {ActivityDetalhesProdutoBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        carregarProduto()

    }

    fun configuraView(produto: Produto) {
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
        when(item.itemId){
            R.id.menu_detalhes_edicao -> {
//                Log.i("menuTeste", "editar")
            }
            R.id.menu_detalhes_remover -> {
//                Log.i("menuTeste", "remover")
            }
        }
        return super.onOptionsItemSelected(item)
    }



}