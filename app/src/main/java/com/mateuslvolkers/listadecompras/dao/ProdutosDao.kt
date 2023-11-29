package com.mateuslvolkers.listadecompras.dao

import com.mateuslvolkers.listadecompras.model.Produto

class ProdutosDao {

    fun adiciona(produto: Produto) {
        produtos.add(produto)
    }
    fun buscarTodos() : List<Produto> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>()
    }
}