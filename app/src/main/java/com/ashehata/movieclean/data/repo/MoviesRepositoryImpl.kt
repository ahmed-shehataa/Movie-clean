package com.ashehata.movieclean.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ashehata.movieclean.data.local.LocalData
import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.data.remote.MoviesPagingSource
import com.ashehata.movieclean.data.remote.PAGE_SIZE_PAGING_EXPLORE
import com.ashehata.movieclean.data.remote.RemoteData
import com.ashehata.movieclean.domain.repo.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val localData: LocalData,
    private val remoteData: RemoteData,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MoviesRepository {


    override suspend fun getPopularMovies(): Pager<Int, MoviesRemoteResponse.Movie> =
        withContext(dispatcher) {
            // get from API
            return@withContext Pager(config = PagingConfig(
                pageSize = PAGE_SIZE_PAGING_EXPLORE,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                MoviesPagingSource(methodCall = { page ->
                    remoteData.getPopularMovies(page)
                })
            })
        }

    override suspend fun getTopRatedMovies(): Pager<Int, MoviesRemoteResponse.Movie> =
        withContext(dispatcher) {
            return@withContext Pager(config = PagingConfig(
                pageSize = PAGE_SIZE_PAGING_EXPLORE,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                MoviesPagingSource(methodCall = { page ->
                    remoteData.getTopRatedMovies(page)
                })
            })
        }

}