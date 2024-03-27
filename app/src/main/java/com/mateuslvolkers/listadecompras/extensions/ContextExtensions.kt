package com.mateuslvolkers.listadecompras.extensions

import android.content.Context
import android.widget.Toast


fun Context.toast(mensagem: String) {
    Toast.makeText(
        this,
        mensagem,
        Toast.LENGTH_SHORT
    ).show()

}