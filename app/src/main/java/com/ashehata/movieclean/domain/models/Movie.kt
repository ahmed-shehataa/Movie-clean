package com.ashehata.movieclean.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Movie(
    val id: Int = 0,
    val movieId: Int = 0,
    val name: String = "",
    val imageUrlSmall: String = "",
    val imageUrlFull: String = "",
    val description: String = "",
    val voteAverage: Double = 0.0,
    val overview: String = "",
    val releaseDate: String = "",
) : Parcelable
