package com.ashehata.movieclean.data.repo

import com.ashehata.movieclean.data.local.LocalData
import com.ashehata.movieclean.data.remote.RemoteData
import com.ashehata.movieclean.domain.models.Movie
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

    override suspend fun getMovies(): List<Movie> = withContext(dispatcher) {
        listOf(
            Movie("ahmed"),
            Movie("momo")
        )
    }

}