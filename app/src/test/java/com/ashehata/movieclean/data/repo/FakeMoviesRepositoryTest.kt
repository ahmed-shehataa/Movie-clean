package com.ashehata.movieclean.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ashehata.movieclean.data.local.LocalData
import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.data.remote.MoviesPagingSource
import com.ashehata.movieclean.data.remote.RemoteData
import com.ashehata.movieclean.domain.repo.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow


class FakeMoviesRepositoryTest(
    private val localData: LocalData,
    private val remoteData: RemoteData,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MoviesRepository {

   /* override suspend fun getPopularMovies(): Flow<List<MoviesRemoteResponse.Movie>> {
        return Pager(config = PagingConfig(
            pageSize = 10
        ), pagingSourceFactory = {
            MoviesPagingSource(methodCall = {
                remoteData.getPopularMovies(1)
            })
        })
    }

    override suspend fun getTopRatedMovies(): Flow<List<MoviesRemoteResponse.Movie>> {
        return Pager(config = PagingConfig(
            pageSize = 10
        ), pagingSourceFactory = {
            MoviesPagingSource(methodCall = {
                remoteData.getTopRatedMovies(1)
            })
        })
    }*/


}