package com.ashehata.movieclean.data.mappers

import com.ashehata.movieclean.data.models.MovieLocal
import com.ashehata.movieclean.data.models.MoviesRemote
import com.ashehata.movieclean.domain.models.Movie

fun MoviesRemote.toMovie(): Movie {
    return Movie(
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.description,
    )
}

fun MovieLocal.toMovie(): Movie {
    return Movie(
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.description,
    )
}