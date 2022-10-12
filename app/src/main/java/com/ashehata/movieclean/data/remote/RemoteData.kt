package com.ashehata.movieclean.data.remote

import com.ashehata.movieclean.data.models.MoviesPopularResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteData {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesPopularResponse
}