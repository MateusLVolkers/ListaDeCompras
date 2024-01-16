package com.mateuslvolkers.listadecompras.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mateuslvolkers.listadecompras.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos() : List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salvar(vararg produto: Produto)

    @Delete
    fun deletarProduto(produto: Produto)
//    @Update
//    fun alterarProduto(produto: Produto)
    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscarPorId(id: Long) : Produto?

//  https://developer.android.com/codelabs/basic-android-kotlin-compose-sql?hl=pt-br#6
    @Query("SELECT * FROM Produto ORDER BY nome ASC")
    fun buscarNomeAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY nome DESC")
    fun buscarNomeDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor ASC")
    fun buscarValorAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor DESC")
    fun buscarValorDesc(): List<Produto>
}