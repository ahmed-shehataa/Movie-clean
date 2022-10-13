package com.ashehata.movieclean.domain.repo

import androidx.paging.Pager
import com.ashehata.movieclean.data.models.MoviesRemoteResponse

interface MoviesRepository {

    suspend fun getPopularMovies(): Pager<Int, MoviesRemoteResponse.Movie>

    suspend fun getTopRatedMovies(): Pager<Int, MoviesRemoteResponse.Movie>
}