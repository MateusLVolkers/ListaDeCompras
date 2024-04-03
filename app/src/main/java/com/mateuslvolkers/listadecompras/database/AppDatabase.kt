package com.mateuslvolkers.listadecompras.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mateuslvolkers.listadecompras.database.converters.Converters
import com.mateuslvolkers.listadecompras.database.dao.ProdutoDao
import com.mateuslvolkers.listadecompras.database.dao.UsuarioDao
import com.mateuslvolkers.listadecompras.database.migration.MIGRATION_1_2
import com.mateuslvolkers.listadecompras.database.migration.MIGRATION_2_3
import com.mateuslvolkers.listadecompras.model.Produto
import com.mateuslvolkers.listadecompras.model.Usuario

@Database(entities = [Produto::class, Usuario::class], version = 3, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {

        private lateinit var db: AppDatabase
        fun instanciaDB(context: Context): AppDatabase {
            if (!::db.isInitialized) {
                synchronized(AppDatabase::class) {
                    db = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "listaCompras.db"
                    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                        .build()
                }
            }
            return db
        }
    }
}

