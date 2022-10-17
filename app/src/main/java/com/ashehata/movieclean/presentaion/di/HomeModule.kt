package com.ashehata.movieclean.presentaion.di

import com.ashehata.movieclean.data.local.MoviesDao
import com.ashehata.movieclean.data.paging.LocalMoviesPagingSource
import com.ashehata.movieclean.data.paging.RemoteMoviesPagingSource
import com.ashehata.movieclean.data.remote.RetrofitApi
import com.ashehata.movieclean.data.repository.MoviesRepositoryImpl
import com.ashehata.movieclean.domain.repo.MoviesRepository
import com.ashehata.movieclean.domain.useCase.MovieUseCase
import com.ashehata.movieclean.domain.useCase.MoviesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class MoviesModule {

    /**
     * Provide an instance of MoviesRepositoryImpl
     */
    @Binds
    abstract fun bindMoviesRepo(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository
    companion object {
        @Provides
        fun providesMoviesRepo(
            localPagingSource: LocalMoviesPagingSource,
            remotePagingSource: RemoteMoviesPagingSource
        ): MoviesRepositoryImpl {
            return MoviesRepositoryImpl(localPagingSource, remotePagingSource)
        }
    }


    /**
     * Provide an instance of MoviesUseCaseImpl
     */
    @Binds
    abstract fun bindMoviesUseCase(moviesUseCaseImpl: MoviesUseCaseImpl): MovieUseCase
}

@Module
@InstallIn(ViewModelComponent::class)
class MoviesImpl {

    @Provides
    @ViewModelScoped
    fun bindMoviesPagingLocal(
        moviesDao: MoviesDao,
    ): LocalMoviesPagingSource {
        return LocalMoviesPagingSource(moviesDao)
    }

    @Provides
    @ViewModelScoped
    fun bindMoviesPagingRemote(
        moviesDao: MoviesDao,
        retrofitApi: RetrofitApi
    ): RemoteMoviesPagingSource {
        return RemoteMoviesPagingSource(retrofitApi, moviesDao)
    }

}