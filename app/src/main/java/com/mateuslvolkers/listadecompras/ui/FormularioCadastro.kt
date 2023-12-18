package com.mateuslvolkers.listadecompras.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.databinding.ActivityFormularioCadastroBinding
import com.mateuslvolkers.listadecompras.extensions.carregarImagem
import com.mateuslvolkers.listadecompras.model.Produto
import com.mateuslvolkers.listadecompras.ui.dialog.FormularioDialog
import java.math.BigDecimal

class FormularioCadastro : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioCadastroBinding.inflate(layoutInflater)
    }

    private var url: String? = null

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
    }


    fun configuraBotaoSalvar() {
        val btnSalvar = binding.btnSalvar

        // .allowMainThreadQueries() não é uma boa prática
        val db = AppDatabase.instanciaDB(this)
        val produtoDao = db.produtoDao()

        btnSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            produtoDao.salvar(produtoNovo)
            finish()
        }
    }

    fun criaProduto(): Produto {
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
            nome = nome,
            descricao = descricao,
            valor = preco,
            imagem = url,
        )
//            Log.i("formulario", "Produto criado: ${produtoCriado}")
//            Log.i("formulario", "Busca no dao: ${dao.buscarTodos()}")
    }
}