package com.ashehata.movieclean.data.repo

import com.ashehata.movieclean.data.local.LocalData
import com.ashehata.movieclean.data.models.MovieLocal

class FakeLocalData(
    private val movies: MutableList<MovieLocal> = mutableListOf()
) : LocalData {

    override suspend fun getMovies(): List<MovieLocal> {
        return movies
    }

    override suspend fun insertMovie(movieLocal: MovieLocal) {
        this.movies.add(movieLocal)
    }

    override suspend fun insertMovies(movies: List<MovieLocal>) {
        this.movies.addAll(movies)
    }

    override suspend fun clearMovies() {
        this.movies.clear()
    }
}