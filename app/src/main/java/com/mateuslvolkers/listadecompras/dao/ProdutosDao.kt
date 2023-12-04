package com.mateuslvolkers.listadecompras.dao

import com.mateuslvolkers.listadecompras.model.Produto
import java.math.BigDecimal

class ProdutosDao {

    fun adiciona(produto: Produto) {
        produtos.add(produto)
    }
    fun buscarTodos() : List<Produto> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto(
                nome = "teste",
                descricao = "descricao",
                valor = BigDecimal("12.30")
            ),
        )
    }
}