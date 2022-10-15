package com.ashehata.movieclean.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey


@Keep
@Entity(tableName = "movies")
data class MovieLocal(
    @PrimaryKey val id: Int? = null,
    val movieId: Int,
    val name: String,
    val imageUrlSmall: String,
    val imageUrlFull: String,
    val description: String,
    val voteAverage: Double = 0.0,
    val overview: String = "",
    val releaseDate: String = "",
)
