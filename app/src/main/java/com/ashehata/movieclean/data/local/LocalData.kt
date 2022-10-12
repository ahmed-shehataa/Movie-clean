package com.ashehata.movieclean.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ashehata.movieclean.data.models.MovieLocal

@Dao
interface LocalData {

    @Query("SELECT * FROM movies")
    suspend fun getMovies() : List<MovieLocal>

    @Insert
    suspend fun insertMovie(movieLocal: MovieLocal)

    @Insert
    suspend fun insertMovies(movies: List<MovieLocal>)

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

}