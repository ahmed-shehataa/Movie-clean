package com.ashehata.movieclean.domain.useCase

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.ashehata.movieclean.data.mappers.toMovie
import com.ashehata.movieclean.domain.models.Movie
import com.ashehata.movieclean.domain.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesUseCaseImpl @Inject constructor(private val moviesRepository: MoviesRepository) :
    MovieUseCase {


    override suspend fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return moviesRepository.getTopRatedMovies().flow.map { moviesList ->
            moviesList.map { movie ->
                movie.toMovie()
            }
        }.distinctUntilChanged()
    }

    override suspend fun getMostPopularMovies(): Flow<PagingData<Movie>> {
        return moviesRepository.getTopRatedMovies().flow.map { moviesList ->
            moviesList.map { movie ->
                movie.toMovie()
            }
        }.distinctUntilChanged()
    }

}