package com.ashehata.movieclean.domain.useCase

import androidx.paging.PagingData
import androidx.paging.map
import com.ashehata.movieclean.data.mappers.movie.toMovie
import com.ashehata.movieclean.domain.models.Movie
import com.ashehata.movieclean.domain.repo.MoviesRepository
import com.ashehata.movieclean.util.Logger
import com.ashehata.movieclean.util.NetworkState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class MoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val networkState: NetworkState = NetworkState
) :
    MovieUseCase {

    private val TAG = "MoviesUseCaseImpl"

    override suspend fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return moviesRepository.getTopRatedMovies().flow.map { moviesList ->
            moviesList.map { movie ->
                movie.toMovie()
            }
        }.distinctUntilChanged()
    }

    override suspend fun getMostPopularMovies(): Flow<PagingData<Movie>> {
        Logger.i(TAG, networkState.isOnline().toString())
        val movies = if (networkState.isOnline()) {
            moviesRepository.getPopularMovies().flow.map { moviesList ->
                moviesList.map { movie ->
                    movie.toMovie()
                }
            }
        } else {
            moviesRepository.getCachedPopularMovies().flow.map { moviesList ->
                moviesList.map { movie ->
                    movie.toMovie()
                }
            }
        }
        return movies.distinctUntilChanged()
    }

}