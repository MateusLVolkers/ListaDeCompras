package com.mateuslvolkers.listadecompras.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mateuslvolkers.listadecompras.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos() : List<Produto>

    @Insert
    fun salvar(vararg produto: Produto)

    @Delete
    fun deletarProduto(produto: Produto)

    @Update
    fun alterarProduto(produto: Produto)

}