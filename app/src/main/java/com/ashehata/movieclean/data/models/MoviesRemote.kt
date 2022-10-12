package com.ashehata.movieclean.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class MoviesRemote(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
)
