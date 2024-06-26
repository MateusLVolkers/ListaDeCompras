package com.mateuslvolkers.listadecompras.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mateuslvolkers.listadecompras.model.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId")
    fun buscaTodosProdutosUsuario(usuarioId: String) : Flow<List<Produto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salvar(vararg produto: Produto)

    @Delete
    fun deletarProduto(produto: Produto)

//    @Update
//    fun alterarProduto(produto: Produto)
    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscarPorId(id: Long): Flow<Produto?>

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