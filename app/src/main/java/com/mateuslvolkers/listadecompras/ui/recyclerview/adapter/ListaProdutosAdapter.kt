package com.mateuslvolkers.listadecompras.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.databinding.ListaProdutosBinding
import com.mateuslvolkers.listadecompras.extensions.carregarImagem
import com.mateuslvolkers.listadecompras.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale


class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto> = emptyList()
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    var clicarEmEditar: (produto: Produto) -> Unit = {}
    var clicarEmRemover: (produto: Produto) -> Unit = {}
    var click: (produto: Produto) -> Unit = {}

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

    inner class ViewHolder(private val binding: ListaProdutosBinding) :
        RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {

        private lateinit var produto: Produto
        init {
            itemView.setOnClickListener {
                if (::produto.isInitialized) {
                    click.invoke(produtos[adapterPosition])
                }
            }
            itemView.setOnLongClickListener {
                PopupMenu(context, itemView).apply {
                    menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
                    setOnMenuItemClickListener(this@ViewHolder)
                }.show()
                true
            }
        }

        fun vincularProduto(produto: Produto) {
            this.produto = produto
            val nome = binding.textProduto
            val descricao = binding.textDescricao
            val preco = binding.textPreco
            val imagem = binding.imgProduto
//            val card = binding.cardProdutos

            nome.text = produto.nome
            descricao.text = produto.descricao
            preco.text = conversorDeMoeda(produto.valor)
            if (produto.imagem.isNullOrBlank()) {
                imagem.visibility = View.GONE
            } else {
                imagem.carregarImagem(produto.imagem)
            }
//            card.setOnClickListener {
//                click(produto)
//            }


        }

        fun conversorDeMoeda(valor: BigDecimal): String {
            val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            val numeroFormatado: String = formatador.format(valor)
            return numeroFormatado
        }
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            item?.let {
                when (it.itemId) {
                    R.id.menu_detalhes_remover -> {
                        clicarEmRemover.invoke(produtos[adapterPosition])
                    }

                    R.id.menu_detalhes_edicao -> {
                        clicarEmEditar.invoke(produtos[adapterPosition])
                    }
                }
            }
            return true
        }

    }

}