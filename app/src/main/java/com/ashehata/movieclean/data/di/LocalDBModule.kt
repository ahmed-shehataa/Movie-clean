package com.ashehata.movieclean.data.di

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ashehata.movieclean.App
import com.ashehata.movieclean.data.local.dataStore.DataStore
import com.ashehata.movieclean.data.local.dataStore.DataStoreImpl
import com.ashehata.movieclean.data.local.room.MoviesDao
import com.ashehata.movieclean.data.local.room.RemoteKeysDao
import com.ashehata.movieclean.data.models.MovieLocal
import com.ashehata.movieclean.data.models.RemoteKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDBModule {

    /*@Provides
    @Singleton
    fun provideApp(): App {
        return App.instance
    }*/

    @Provides
    @Singleton
    fun provideRoomDB(
        applicationContext: Application
    ): AppDatabase {
        return Room.databaseBuilder(
            (applicationContext as Context),
            AppDatabase::class.java, "movie-db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDataStore(
        myApp: App,
    ): DataStore {
        return DataStoreImpl(myApp.applicationContext)
    }

}

@Database(entities = [MovieLocal::class, RemoteKeys::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}