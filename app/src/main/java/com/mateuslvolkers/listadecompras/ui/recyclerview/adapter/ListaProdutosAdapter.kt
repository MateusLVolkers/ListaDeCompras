package com.mateuslvolkers.listadecompras.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.databinding.ListaProdutosBinding
import com.mateuslvolkers.listadecompras.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale


class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto>
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ListaProdutosBinding.inflate(inflater, parent, false)
//        val view = inflater.inflate(R.layout.lista_produtos, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return produtos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincularProduto(produto)
    }

    fun atualizar(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ListaProdutosBinding) : RecyclerView.ViewHolder(binding.root) {
        fun vincularProduto(produto: Produto) {
            val nome = binding.textProduto
            val descricao = binding.textDescricao
            val preco = binding.textPreco

            nome.text = produto.nome
            descricao.text = produto.descricao
            preco.text = conversorDeMoeda(produto.valor)
        }

        fun conversorDeMoeda(valor: BigDecimal) : String  {
            val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            val numeroFormatado: String = formatador.format(valor)
            return numeroFormatado
        }

    }

}