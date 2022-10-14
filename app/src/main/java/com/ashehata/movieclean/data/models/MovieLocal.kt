package com.ashehata.movieclean.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
data class MovieLocal(
    @PrimaryKey val id: Int? = null,
    val isTopRated: Boolean,
    val name: String = "",
    val imageUrlFull: String = "",
    val imageUrlIcon: String = "",
    val description: String = "",
    val voteAverage: Double = 0.0,
    val overview: String = "",
    val releaseDate: String = "",
)
