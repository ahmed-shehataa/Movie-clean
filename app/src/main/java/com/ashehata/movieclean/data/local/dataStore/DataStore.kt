package com.ashehata.movieclean.data.local.dataStore

interface DataStore {

    suspend fun updateRemoteKey(key: Int)
    suspend fun getRemoteKey(default: Int = 1): Int
    suspend fun clearAll()
}
