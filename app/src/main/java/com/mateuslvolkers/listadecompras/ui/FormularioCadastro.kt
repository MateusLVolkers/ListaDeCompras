package com.mateuslvolkers.listadecompras.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.dao.ProdutosDao
import com.mateuslvolkers.listadecompras.databinding.ActivityFormularioCadastroBinding
import com.mateuslvolkers.listadecompras.databinding.ActivityMainBinding
import com.mateuslvolkers.listadecompras.model.Produto
import java.math.BigDecimal
import kotlin.math.log

class FormularioCadastro : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioCadastroBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        binding.ivFormulario.setOnClickListener {
//            Log.i("image","clicado")
            AlertDialog.Builder(this)
                .setView(R.layout.dialog_formulario_imagem)
                .show()
        }




    }


    fun configuraBotaoSalvar() {
        val btnSalvar = binding.btnSalvar
        btnSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            val dao = ProdutosDao()
            dao.adiciona(produtoNovo)
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
        )
//            Log.i("formulario", "Produto criado: ${produtoCriado}")
//            Log.i("formulario", "Busca no dao: ${dao.buscarTodos()}")
    }
}