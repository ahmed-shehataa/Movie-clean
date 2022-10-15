package com.ashehata.movieclean

object NetworkState {

    @Volatile
    private var isOnline: Boolean = false

    fun setNetworkState(isOnline: Boolean) {
        synchronized(this) {
            this.isOnline = isOnline
        }
    }

    fun isOnline(): Boolean {
        synchronized(this) {
            return isOnline
        }
    }
}