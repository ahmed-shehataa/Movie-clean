package com.ashehata.movieclean.data.repository

import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.data.remote.RetrofitApi

class FakeRetrofitApi(
    private val popularMovies: MutableList<MoviesRemoteResponse.Movie> = mutableListOf(),
    private val topRatedMovies: MutableList<MoviesRemoteResponse.Movie> = mutableListOf(),
) : RetrofitApi {


    override suspend fun getPopularMovies(page: Int): MoviesRemoteResponse {
        return MoviesRemoteResponse(
            page = 1, movies = popularMovies, totalPages = 500,
            totalResults = null
        )
    }

    override suspend fun getTopRatedMovies(page: Int): MoviesRemoteResponse {
        return MoviesRemoteResponse(
            page = 1, movies = topRatedMovies, totalPages = 500,
            totalResults = null
        )
    }

    fun insertPopularMovies(movies: List<MoviesRemoteResponse.Movie>) {
        popularMovies.addAll(movies)

    }

    fun insertTopRatedMovies(movies: List<MoviesRemoteResponse.Movie>) {
        topRatedMovies.addAll(movies)
    }
}