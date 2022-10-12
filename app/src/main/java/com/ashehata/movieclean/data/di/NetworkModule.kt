package com.ashehata.movieclean.data.di

import com.ashehata.movieclean.App
import com.ashehata.movieclean.BASE_URL
import com.ashehata.movieclean.BuildConfig
import com.ashehata.movieclean.data.local.LocalData
import com.ashehata.movieclean.data.remote.RemoteData
import com.ashehata.movieclean.data.repo.MoviesRepositoryImpl
import com.ashehata.movieclean.domain.repo.MoviesRepository
import com.ashehata.movieclean.domain.useCase.MovieUseCase
import com.ashehata.movieclean.domain.useCase.MoviesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApp(): App {
        return App.instance
    }

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.apply {
            retryOnConnectionFailure(true)
            readTimeout(1, TimeUnit.MINUTES)
            connectTimeout(1, TimeUnit.MINUTES)
            writeTimeout(5, TimeUnit.MINUTES)
            addInterceptor(logging)
        }.build()
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun getMoshi(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient, moshi: MoshiConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshi)
            .build()
    }

    @Provides
    @Singleton
    fun provideAPIService(
        retrofit: Retrofit,
    ): RemoteData {
        return retrofit.create(RemoteData::class.java)
    }


    @Provides
    @Singleton
    fun bindMoviesRepo(
        localData: LocalData,
        remoteData: RemoteData
    ): MoviesRepository {
        return MoviesRepositoryImpl(localData, remoteData)
    }

    @Provides
    @Singleton
    fun bindMoviesUseCase(
        moviesRepository: MoviesRepository
    ): MovieUseCase {
        return MoviesUseCaseImpl(moviesRepository)
    }


}