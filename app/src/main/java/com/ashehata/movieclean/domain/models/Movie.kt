package com.ashehata.movieclean.domain.models

data class Movie(
    val id: Int,
    val name: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val voteAverage: Double = 0.0,
    val overview: String = "",
    val releaseDate: String = "",
)
