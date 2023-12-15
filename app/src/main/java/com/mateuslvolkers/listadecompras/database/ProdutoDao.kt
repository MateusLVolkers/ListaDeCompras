package com.mateuslvolkers.listadecompras.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mateuslvolkers.listadecompras.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos() : List<Produto>

    @Insert
    fun salvar(vararg produto: Produto)

}