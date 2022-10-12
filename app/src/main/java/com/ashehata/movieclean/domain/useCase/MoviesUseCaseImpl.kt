package com.ashehata.movieclean.domain.useCase

import com.ashehata.movieclean.domain.models.Movie
import com.ashehata.movieclean.domain.repo.MoviesRepository
import javax.inject.Inject

class MoviesUseCaseImpl @Inject constructor(private val moviesRepository: MoviesRepository) : MovieUseCase {


    override suspend fun getTopRatedMovies(): List<Movie> {
        return moviesRepository.getMovies()
    }

    override suspend fun getMostPopularMovies(): List<Movie> {
        return emptyList()
    }

}