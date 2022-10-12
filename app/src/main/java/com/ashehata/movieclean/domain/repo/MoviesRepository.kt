package com.ashehata.movieclean.domain.repo

import com.ashehata.movieclean.domain.models.Movie

interface MoviesRepository {

    suspend fun getMovies(): List<Movie>
}