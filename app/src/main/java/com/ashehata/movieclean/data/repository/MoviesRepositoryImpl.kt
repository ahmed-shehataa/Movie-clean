package com.ashehata.movieclean.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ashehata.movieclean.data.models.MovieLocal
import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.data.paging.LocalMoviesPagingSource
import com.ashehata.movieclean.data.paging.RemoteMoviesPagingSource
import com.ashehata.movieclean.data.paging.MoviesType
import com.ashehata.movieclean.data.util.PAGE_SIZE_PAGING_LOCAL_MOVIE
import com.ashehata.movieclean.data.util.PAGE_SIZE_PAGING_REMOTE_MOVIE
import com.ashehata.movieclean.domain.repo.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val moviesLocalPagingSource: LocalMoviesPagingSource,
    private val remoteMoviesPagingSource: RemoteMoviesPagingSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MoviesRepository {


    override suspend fun getPopularMovies(): Pager<Int, MoviesRemoteResponse.Movie> =
        withContext(dispatcher) {
            // change movies type
            remoteMoviesPagingSource.setMoviesType(MoviesType.POPULAR)
            remoteMoviesPagingSource.setForceCaching(true)
            // get from API
            return@withContext Pager(config = PagingConfig(
                pageSize = PAGE_SIZE_PAGING_REMOTE_MOVIE,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                remoteMoviesPagingSource
            })
        }

    override suspend fun getTopRatedMovies(): Pager<Int, MoviesRemoteResponse.Movie> =
        withContext(dispatcher) {
            remoteMoviesPagingSource.setMoviesType(MoviesType.TOP_RATED)
            remoteMoviesPagingSource.setForceCaching(false)
            return@withContext Pager(config = PagingConfig(
                pageSize = PAGE_SIZE_PAGING_REMOTE_MOVIE,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                remoteMoviesPagingSource
            })
        }

    override suspend fun getCachedPopularMovies(): Pager<Int, MovieLocal> =
        withContext(dispatcher) {
            return@withContext Pager(config = PagingConfig(
                pageSize = PAGE_SIZE_PAGING_LOCAL_MOVIE,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                moviesLocalPagingSource
            })
        }

}