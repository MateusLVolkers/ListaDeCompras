package com.mateuslvolkers.listadecompras.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.model.Produto


class ListaProdutosAdapter(
    private val context: Context, private val produtos: List<Produto>
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.lista_produtos, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return produtos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincularProduto(produto)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun vincularProduto(produto: Produto) {
            val nome = itemView.findViewById<TextView>(R.id.textProduto)
            val descricao = itemView.findViewById<TextView>(R.id.textDescricao)
            val preco = itemView.findViewById<TextView>(R.id.textPreco)

            nome.text = produto.nome
            descricao.text = produto.descricao
            preco.text = produto.valor.toPlainString()
        }

    }

}