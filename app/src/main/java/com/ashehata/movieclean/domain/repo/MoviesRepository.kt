package com.ashehata.movieclean.domain.repo

import androidx.paging.Pager
import com.ashehata.movieclean.data.models.MoviesPopularResponse

interface MoviesRepository {

    suspend fun getPopularMovies(): Pager<Int, MoviesPopularResponse.Movie>

    suspend fun getTopRatedMovies(): Pager<Int, MoviesPopularResponse.Movie>
}