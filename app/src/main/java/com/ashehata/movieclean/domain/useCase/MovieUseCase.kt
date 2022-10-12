package com.ashehata.movieclean.domain.useCase

import com.ashehata.movieclean.domain.models.Movie
import dagger.Provides


interface MovieUseCase {

    suspend fun getTopRatedMovies(): List<Movie>

    suspend fun getMostPopularMovies(): List<Movie>
}