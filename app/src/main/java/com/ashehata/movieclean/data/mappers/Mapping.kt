package com.ashehata.movieclean.data.mappers

import com.ashehata.movieclean.data.models.MovieLocal
import com.ashehata.movieclean.data.models.MoviesPopularResponse
import com.ashehata.movieclean.data.util.toRealPath
import com.ashehata.movieclean.domain.models.Movie


fun MoviesPopularResponse.Movie.toMovie(): Movie {
    return Movie(
        id = this.id ?: -1,
        name = this.title ?: "",
        imageUrl = this.backdropPath.toRealPath(),
        description = this.overview ?: "",
        voteAverage = this.voteAverage ?: 0.0,
        overview = this.overview ?: "",
        releaseDate = this.releaseDate ?: ""
    )
}

fun MovieLocal.toMovie(): Movie {
    return Movie(
        id = this.id ?: -1,
        name = this.name ?: "",
        imageUrl = this.imageUrl,
        description = this.overview ?: "",
        voteAverage = this.voteAverage ?: 0.0,
        overview = this.overview ?: "",
        releaseDate = this.releaseDate ?: ""
    )
}