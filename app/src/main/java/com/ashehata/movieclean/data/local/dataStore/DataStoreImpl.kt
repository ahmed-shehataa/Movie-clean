package com.ashehata.movieclean.data.local.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private val Context.dataStore by preferencesDataStore("user_data")

class DataStoreImpl(
    appContext: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataStore {

    private val mDataStore by lazy {
        appContext.dataStore
    }

    companion object {
        const val LAST_KEY = "lastKey"
    }

    override suspend fun getRemoteKey(default: Int): Int {
        return withContext(dispatcher) {
            mDataStore.data.map { settings ->
                settings[intPreferencesKey(LAST_KEY)] ?: default
            }.first()
        }
    }

    override suspend fun updateRemoteKey(key: Int) {
        withContext(dispatcher) {
            mDataStore.edit { settings ->
                settings[intPreferencesKey(LAST_KEY)] = key
            }
        }
    }

    override suspend fun clearAll() {
        mDataStore.edit { it.clear() }
    }

}