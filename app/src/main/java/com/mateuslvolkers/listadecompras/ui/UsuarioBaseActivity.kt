package com.mateuslvolkers.listadecompras.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.mateuslvolkers.listadecompras.database.AppDatabase
import com.mateuslvolkers.listadecompras.model.Usuario
import com.mateuslvolkers.listadecompras.preferences.dataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UsuarioBaseActivity : AppCompatActivity() {

    private val usuarioDao by lazy {
        val db = AppDatabase.instanciaDB(this)
        db.usuarioDao()
    }
    private val contextoCorrotinaIO: CoroutineDispatcher = Dispatchers.IO
    private val _usuario: MutableStateFlow<Usuario?> = MutableStateFlow(null)
    protected val usuario: StateFlow<Usuario?> = _usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            verificaUsuarioLogado()
        }
    }

    private fun buscarUsuario(usuarioID: String) = lifecycleScope.launch {
        launch {
            withContext(contextoCorrotinaIO) {
                _usuario.value = usuarioDao.buscarPorId(usuarioID).firstOrNull()
            }
        }
    }

    protected suspend fun deslogarUsuario() {
        dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey("usuarioLogado"))
        }
    }

    private fun iniciaLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    protected suspend fun verificaUsuarioLogado() {
        dataStore.data.collect { preferences ->
            preferences[stringPreferencesKey("usuarioLogado")]?.let { usuarioID ->
                //Log.i("usuarioID", "Recebido: $usuarioID")
                buscarUsuario(usuarioID)
            } ?: iniciaLogin()
        }
    }
}