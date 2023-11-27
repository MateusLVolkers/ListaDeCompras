package com.mateuslvolkers.listadecompras.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.model.Produto
import java.math.BigDecimal

class FormularioCadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_cadastro)
        val btnSalvar = findViewById<Button>(R.id.btn_salvar)


        btnSalvar.setOnClickListener {
            val edtProduto = findViewById<EditText>(R.id.edt_produto)
            val edtDescricao = findViewById<EditText>(R.id.edt_descricao)
            val edtPreco = findViewById<EditText>(R.id.edt_preco)

            val nome = edtProduto.text.toString()
            val descricao = edtDescricao.text.toString()
            val precoEmTexto = edtPreco.text.toString()

            val preco = if(precoEmTexto.isBlank()) {
                BigDecimal.ZERO
            } else {
                BigDecimal(precoEmTexto)
            }

//            Log.i("formulario", "Nome: ${nome}")
//            Log.i("formulario", "Descricao: ${descricao}")
//            Log.i("formulario", "Preco: ${preco}")
//            Log.i("formulario", preco.javaClass.name)

            val produtoCriado = Produto(
                nome = nome,
                descricao = descricao,
                valor = preco,
            )
//            Log.i("NovoProduto", "Produto criado: ${produtoCriado}")
        }
    }

}