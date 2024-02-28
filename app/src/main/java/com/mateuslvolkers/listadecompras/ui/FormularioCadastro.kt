package com.mateuslvolkers.listadecompras.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.databinding.ActivityFormularioCadastroBinding
import com.mateuslvolkers.listadecompras.extensions.carregarImagem
import com.mateuslvolkers.listadecompras.model.Produto
import com.mateuslvolkers.listadecompras.ui.dialog.FormularioDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class FormularioCadastro : AppCompatActivity() {

    private val binding by lazy { ActivityFormularioCadastroBinding.inflate(layoutInflater) }
    private val produtoDao by lazy {
        val db = AppDatabase.instanciaDB(this)
        db.produtoDao()
    }
    private var url: String? = null
    private var produtoId = 0L
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configuraBotaoSalvar()
        binding.ivFormulario.setOnClickListener {
            FormularioDialog(this).show(url) { imagem ->
                url = imagem
                binding.ivFormulario.carregarImagem(url)
            }
        }
        recebeProduto()
    }

    override fun onResume() {
        super.onResume()
        buscarProdutoBanco()
    }

    private fun buscarProdutoBanco() {
        scope.launch {
            val produtoRecebido = produtoDao.buscarPorId(produtoId)
            produtoRecebido.collect {
                it?.let {
                    val produto = it
                    withContext(Main) {
                        preencherCampos(produto)
                    }
                }
            }
        }
    }

    private fun recebeProduto() {
        produtoId = intent.getLongExtra("produtoID", 0L)
    }

    private fun preencherCampos(produtoRecebido: Produto) {
        title = "Alterar produto"
        url = produtoRecebido.imagem
        binding.apply {
            ivFormulario.carregarImagem(produtoRecebido.imagem)
            edtProduto.setText(produtoRecebido.nome)
            edtDescricao.setText(produtoRecebido.descricao)
            edtPreco.setText(produtoRecebido.valor.toPlainString())
        }
    }

    private fun configuraBotaoSalvar() {
        val btnSalvar = binding.btnSalvar
        btnSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
//            if (produtoId > 0) {
//                produtoDao.alterarProduto(produtoNovo)
//            } else {
//                produtoDao.salvar(produtoNovo)
//            }
            scope.launch {
                produtoDao.salvar(produtoNovo)
                finish()
            }
        }
    }

    private fun criaProduto(): Produto {
        val edtProduto = binding.edtProduto
        val edtDescricao = binding.edtDescricao
        val edtPreco = binding.edtPreco

        val nome = edtProduto.text.toString()
        val descricao = edtDescricao.text.toString()
        val precoEmTexto = edtPreco.text.toString()

        val preco = if (precoEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(precoEmTexto)
        }
//            Log.i("formulario", "Nome: ${nome}")
//            Log.i("formulario", "Descricao: ${descricao}")
//            Log.i("formulario", "Preco: ${preco}")
//            Log.i("formulario", preco.javaClass.name)
        return Produto(
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = preco,
            imagem = url,
        )
//            Log.i("formulario", "Produto criado: ${produtoCriado}")
//            Log.i("formulario", "Busca no dao: ${dao.buscarTodos()}")
    }
}