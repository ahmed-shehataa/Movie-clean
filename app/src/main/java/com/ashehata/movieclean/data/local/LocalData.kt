package com.ashehata.movieclean.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ashehata.movieclean.data.models.MovieLocal

@Dao
interface LocalData {

    @Query("SELECT * FROM movies ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getMovies(limit: Int, offset: Int) : List<MovieLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieLocal: MovieLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieLocal>)

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

}