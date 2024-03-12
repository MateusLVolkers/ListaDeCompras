package com.mateuslvolkers.listadecompras.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class Usuario(
    @PrimaryKey
    val id: String,
    val nome: String,
    val senha:String
) : Parcelable
