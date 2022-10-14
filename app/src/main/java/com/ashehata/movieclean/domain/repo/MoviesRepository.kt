package com.ashehata.movieclean.domain.repo

import androidx.paging.Pager
import com.ashehata.movieclean.data.models.MovieLocal
import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.domain.models.Movie

interface MoviesRepository {

    suspend fun getPopularMovies(): Pager<Int, MovieLocal>

    suspend fun getTopRatedMovies(): Pager<Int, MoviesRemoteResponse.Movie>
}