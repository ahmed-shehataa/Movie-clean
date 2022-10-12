package com.ashehata.movieclean.data.di

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ashehata.movieclean.App
import com.ashehata.movieclean.BuildConfig
import com.ashehata.movieclean.data.local.LocalData
import com.ashehata.movieclean.data.models.MovieLocal
import com.ashehata.movieclean.data.remote.RemoteData
import com.reloaded.cat.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
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
    ): LocalData {
        return Room.databaseBuilder(
            (applicationContext as Context),
            AppDatabase::class.java, "movie-db"
        ).build().movieDao()
    }

}

@Database(entities = [MovieLocal::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): LocalData
}