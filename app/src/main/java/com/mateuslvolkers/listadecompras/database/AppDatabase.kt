package com.mateuslvolkers.listadecompras.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mateuslvolkers.listadecompras.model.Produto

@Database(entities = arrayOf(Produto::class), version = 1 )
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao() : ProdutoDao

    companion object{
        fun instanciaDB(context: Context) : AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "listaCompras.db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}
