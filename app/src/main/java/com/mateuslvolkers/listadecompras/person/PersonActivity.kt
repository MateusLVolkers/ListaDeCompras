package com.mateuslvolkers.listadecompras.person

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.mateuslvolkers.listadecompras.R
import com.mateuslvolkers.listadecompras.databinding.ActivityPersonBinding
import com.mateuslvolkers.listadecompras.person.model.Person

class PersonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonBinding
    private val adapter by lazy { PersonAdapter() }
    private val repository by lazy { PersonRepository() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
    }

    private fun setupList() {
        binding.perRecycler.adapter = adapter
        adapter.data = repository.getPersons()
        adapter.setOnItemClickListener { person ->
            Log.i(LOG_TAG, "person: $person")
            Toast.makeText(this, "person: $person", Toast.LENGTH_SHORT).show()
        }

        adapter.onEditClickListener = { person ->
            Toast.makeText(this, "Editou: $person", Toast.LENGTH_SHORT).show()
        }

        adapter.onRemoveClickListener = { person ->
            Toast.makeText(this, "Remover: $person", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val LOG_TAG = "XRL8"
    }
}