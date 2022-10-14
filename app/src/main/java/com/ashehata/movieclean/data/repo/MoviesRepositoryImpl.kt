package com.ashehata.movieclean.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ashehata.movieclean.data.di.AppDatabase
import com.ashehata.movieclean.data.local.dataStore.DataStore
import com.ashehata.movieclean.data.models.MovieLocal
import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.data.remote.MoviesPagingSource
import com.ashehata.movieclean.data.remote.PAGE_SIZE_PAGING_EXPLORE
import com.ashehata.movieclean.data.remote.RemoteData
import com.ashehata.movieclean.data.remoteMediator.MoviesRemoteMediator
import com.ashehata.movieclean.domain.repo.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val remoteData: RemoteData,
    private val dataStore: DataStore,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MoviesRepository {

    //private val localData = appDatabase.movieDao()

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPopularMovies(): Pager<Int, MovieLocal> =
        withContext(dispatcher) {
            // get from API
            return@withContext Pager(
                config = PagingConfig(pageSize = PAGE_SIZE_PAGING_EXPLORE),
                remoteMediator = MoviesRemoteMediator(forceRefresh = false, appDatabase, remoteData, dataStore)
            ) {
                appDatabase.movieDao().getMovies(isTopRatedOnly = false)
            }
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