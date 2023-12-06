package com.mateuslvolkers.listadecompras.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import coil.load
import com.mateuslvolkers.listadecompras.databinding.ActivityDetalhesProdutoBinding
import com.mateuslvolkers.listadecompras.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class DetalhesProduto : AppCompatActivity() {

    private val binding by lazy {ActivityDetalhesProdutoBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bundle = intent.extras

        val produto = if (Build.VERSION.SDK_INT >= 33) {
            bundle?.getParcelableArray("produto", Produto::class.java)
        } else {
            bundle?.getParcelable("produto")
        }

        Log.i("produtoclicado", "${produto}")

    }

    fun verificaBundle(bundle: Bundle?) {
        if (bundle == null) {
            finish()
        }
    }

    fun configuraView(produto: Produto) {
        binding.apply {
            titleDetalhe.text = produto.nome
            textDescricao.text = produto.descricao
            textPreco.text = conversorDeMoeda(produto.valor)
            imagemDetalhes.load(produto.imagem)
        }
    }

    fun conversorDeMoeda(valor: BigDecimal) : String  {
        val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
        val numeroFormatado: String = formatador.format(valor)
        return numeroFormatado
    }

}