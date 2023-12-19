package com.mateuslvolkers.listadecompras.person

import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.databinding.ItemPersonBinding
import com.mateuslvolkers.listadecompras.person.PersonActivity.Companion.LOG_TAG
import com.mateuslvolkers.listadecompras.person.model.Person

class PersonAdapter : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    var data: List<Person> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var _onItemClickListener: (Person) -> Unit = {}
    var onEditClickListener: (Person) -> Unit = {}
    var onRemoveClickListener: (Person) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        Log.i(LOG_TAG, "onCreateViewHolder")
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context))

        return PersonViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        Log.i(LOG_TAG, "onBindViewHolder p: $position")
        holder.bind(data[position])
    }

    fun setOnItemClickListener(action : (Person) -> Unit) {
        _onItemClickListener = action
    }

    inner class PersonViewHolder(private val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {

        init {
            itemView.setOnClickListener {
                _onItemClickListener.invoke(data[adapterPosition])
            }

            itemView.setOnLongClickListener {
                PopupMenu(itemView.context, itemView).apply {
                    menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
                    setOnMenuItemClickListener(this@PersonViewHolder)
                }.show()
                true
            }
        }

        fun bind(person: Person) {
            binding.perTxtName.text = person.name
            binding.perTxtDescription.text = person.description
        }

        override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
            menuItem?.let {
                when (it.itemId) {
                    R.id.menu_detalhes_remover -> {
                        onRemoveClickListener.invoke(data[adapterPosition])
                    }

                    R.id.menu_detalhes_edicao -> {
                        onEditClickListener.invoke(data[adapterPosition])
                    }
                }
            }
            return true
        }
    }
}