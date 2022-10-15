package com.ashehata.movieclean.domain.repo

import androidx.paging.Pager
import androidx.paging.PagingSource
import com.ashehata.movieclean.data.models.MovieLocal
import com.ashehata.movieclean.data.models.MoviesRemoteResponse

interface MoviesRepository {

    suspend fun getPopularMovies(): Pager<Int, MoviesRemoteResponse.Movie>

    suspend fun getTopRatedMovies(): Pager<Int, MoviesRemoteResponse.Movie>

    suspend fun getCachedPopularMovies(): Pager<Int, MovieLocal>
}