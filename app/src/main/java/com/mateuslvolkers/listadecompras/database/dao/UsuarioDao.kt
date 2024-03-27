package com.mateuslvolkers.listadecompras.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mateuslvolkers.listadecompras.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert
    fun salvar(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE id = :usuarioID AND senha = :senha")
    fun autenticaUsuario(usuarioID: String, senha: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE id = :usuarioID")
    fun buscarPorId(usuarioID: String): Flow<Usuario>

}