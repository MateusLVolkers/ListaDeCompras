package com.mateuslvolkers.listadecompras.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.mateuslvolkers.listadecompras.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    fun salvar(usuario: Usuario)

}