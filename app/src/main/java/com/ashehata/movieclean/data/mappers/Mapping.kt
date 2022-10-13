package com.ashehata.movieclean.data.mappers

import com.ashehata.movieclean.data.mappers.image.Image780
import com.ashehata.movieclean.data.mappers.image.Image185
import com.ashehata.movieclean.data.models.MovieLocal
import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.domain.models.Movie


fun MoviesRemoteResponse.Movie.toMovie(): Movie {
    return Movie(
        id = this.id ?: -1,
        name = this.title ?: "",
        imageUrlSmall = Image185(imagePath = this.backdropPath).getFullImageUrl(),
        imageUrlFull = Image780(imagePath = this.backdropPath).getFullImageUrl(),
        description = this.overview ?: "",
        voteAverage = this.voteAverage ?: 0.0,
        overview = this.overview ?: "",
        releaseDate = this.releaseDate ?: "",
    )
}

fun MovieLocal.toMovie(): Movie {
    return Movie(
        id = this.id ?: -1,
        name = this.name ?: "",
        imageUrlSmall = this.imageUrl,
        description = this.overview ?: "",
        voteAverage = this.voteAverage ?: 0.0,
        overview = this.overview ?: "",
        releaseDate = this.releaseDate ?: "",
    )
}