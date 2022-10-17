package com.ashehata.movieclean.data.mappers

import com.ashehata.movieclean.data.mappers.image.Image780
import com.ashehata.movieclean.data.mappers.image.Image185
import com.ashehata.movieclean.data.models.MovieLocal
import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.domain.models.Movie


fun MoviesRemoteResponse.Movie.toMovie(): Movie {
    return Movie(
        id = -1,
        movieId = this.movieId ?: -1,
        name = this.title ?: "",
        imageUrlSmall = backdropPath?.let { Image185(imagePath = this.backdropPath).getFullImageUrl() } ?: "",
        imageUrlFull = backdropPath?.let { Image780(imagePath = this.backdropPath).getFullImageUrl() } ?: "",
        description = this.overview ?: "",
        voteAverage = this.voteAverage ?: 0.0,
        overview = this.overview ?: "",
        releaseDate = this.releaseDate ?: "",
    )
}

fun MoviesRemoteResponse.Movie.toLocalMovie(): MovieLocal {
    return MovieLocal(
        id = null,
        movieId = this.movieId ?: -1,
        name = this.title ?: "",
        imageUrlSmall = backdropPath?.let { Image185(imagePath = this.backdropPath).getFullImageUrl() } ?: "",
        imageUrlFull = backdropPath?.let { Image780(imagePath = this.backdropPath).getFullImageUrl() } ?: "",
        description = this.overview ?: "",
        voteAverage = this.voteAverage ?: 0.0,
        overview = this.overview ?: "",
        releaseDate = this.releaseDate ?: "",
    )
}

fun MovieLocal.toMovie(): Movie {
    return Movie(
        id = this.id ?: -1,
        movieId = this.movieId,
        name = this.name ?: "",
        imageUrlSmall = this.imageUrlSmall,
        imageUrlFull = this.imageUrlFull,
        description = this.overview ?: "",
        voteAverage = this.voteAverage ?: 0.0,
        overview = this.overview ?: "",
        releaseDate = this.releaseDate ?: "",
    )
}