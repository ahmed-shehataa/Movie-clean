package com.ashehata.movieclean.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ashehata.movieclean.data.models.MovieLocal

@Dao
interface MoviesDao {

    /**
     * Get the [Popular or TopRated] movies
     * @param isTopRatedOnly true: to return the TopRated movies,
     *                      false: to return need the Popular movies
     */
    @Query("SELECT * FROM movies WHERE isTopRated == :isTopRatedOnly")
    fun getMovies(isTopRatedOnly: Boolean = false): PagingSource<Int, MovieLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieLocal: MovieLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieLocal>)

    @Query("DELETE FROM movies WHERE isTopRated == 1")
    suspend fun clearTopRatedMovies()

    @Query("DELETE FROM movies WHERE isTopRated == 0")
    suspend fun clearPopularMovies()

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()


}