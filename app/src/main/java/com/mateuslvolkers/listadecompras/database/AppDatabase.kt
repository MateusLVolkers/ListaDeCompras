package com.mateuslvolkers.listadecompras.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mateuslvolkers.listadecompras.model.Produto

@Database(entities = arrayOf(Produto::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao

    companion object {

        private lateinit var db: AppDatabase
        fun instanciaDB(context: Context): AppDatabase {
            if (!::db.isInitialized) {
                synchronized(AppDatabase::class) {
                    db = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "listaCompras.db"
                    ).build()
                }
            }
            return db
        }
    }
}

