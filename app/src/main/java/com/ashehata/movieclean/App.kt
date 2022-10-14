package com.ashehata.movieclean

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.ashehata.movieclean.util.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Enable Logger in logcat
        if (BuildConfig.DEBUG) {
            Logger.isEnabled = true
        }

        //TODO apply dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun context(): Context = applicationContext

}