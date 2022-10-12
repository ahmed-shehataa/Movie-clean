package com.ashehata.movieclean.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
data class MovieLocal(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
)
