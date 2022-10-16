package com.ashehata.movieclean.data.remote

import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesRemoteResponse

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): MoviesRemoteResponse

}