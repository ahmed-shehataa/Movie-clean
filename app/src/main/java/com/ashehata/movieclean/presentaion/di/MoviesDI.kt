package com.ashehata.movieclean.presentaion.di

import com.ashehata.movieclean.data.di.NetworkModule
import com.ashehata.movieclean.data.repo.MoviesRepositoryImpl
import com.ashehata.movieclean.domain.repo.MoviesRepository
import com.ashehata.movieclean.domain.useCase.MovieUseCase
import com.ashehata.movieclean.domain.useCase.MoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class MoviesDI {


   /* @Binds
    abstract fun bindMoviesRepo(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository*/

    /*@Binds
    abstract fun bindMoviesUseCase(
        moviesUseCaseImpl: MoviesUseCaseImpl
    ): MovieUseCase*/



}