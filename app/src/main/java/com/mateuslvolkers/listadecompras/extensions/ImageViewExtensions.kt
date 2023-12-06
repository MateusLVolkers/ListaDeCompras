package com.mateuslvolkers.listadecompras.extensions

import android.widget.ImageView
import coil.load
import com.mateuslvolkers.listadecompras.R

fun ImageView.carregarImagem(url: String? = null) {
    load(url){
        fallback(com.google.android.material.R.drawable.mtrl_ic_error)
        error(com.google.android.material.R.drawable.mtrl_ic_error)
        placeholder(R.drawable.placeholder)
    }

}