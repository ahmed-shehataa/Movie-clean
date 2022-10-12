package com.ashehata.movieclean.data.remote

import com.ashehata.movieclean.data.models.MoviesRemote
import retrofit2.http.GET

interface RemoteData {

    @GET
    suspend fun getMovies() : MoviesRemote
}