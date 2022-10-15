package com.ashehata.movieclean.data.mappers

import com.ashehata.movieclean.data.mappers.image.Image185
import com.ashehata.movieclean.data.mappers.image.Image780
import com.ashehata.movieclean.data.models.MoviesRemoteResponse
import com.ashehata.movieclean.domain.models.Movie
import com.google.common.truth.Truth.assertThat
import org.junit.Test


class MovieMappingKtTest {

    @Test
    fun `toMovie() valid remote movie to movie`() {
        val remoteMovie = MoviesRemoteResponse.Movie(
            adult = false,
            backdropPath = "/iuFbU5jiNh8DAxLBGifZCvv3KmB.jpg",
            movieId = 4935,
            originalLanguage = "ja",
            originalTitle = "ハウルの動く城",
            overview = "When Sophie, a shy young woman, is cursed with an old body by a spiteful witch, her only chance of breaking the spell lies with a self-indulgent yet insecure young wizard and his companions in his legged, walking castle.",
            popularity = 87.188,
            posterPath = "/6pZgH10jhpToPcf0uvyTCPFhWpI.jpg",
            releaseDate = "2004-09-09",
            title = "Howl's Moving Castle",
            video = false,
            voteAverage = 8.4,
            voteCount = 7673
        )

        val movie = Movie(
            id = 4935,
            name = "Howl's Moving Castle",
            imageUrlSmall = Image185(imagePath = "/iuFbU5jiNh8DAxLBGifZCvv3KmB.jpg").getFullImageUrl(),
            imageUrlFull = Image780(imagePath = "/iuFbU5jiNh8DAxLBGifZCvv3KmB.jpg").getFullImageUrl(),
            description = "When Sophie, a shy young woman, is cursed with an old body by a spiteful witch, her only chance of breaking the spell lies with a self-indulgent yet insecure young wizard and his companions in his legged, walking castle.",
            voteAverage = 8.4,
            releaseDate = "2004-09-09",
            overview = "When Sophie, a shy young woman, is cursed with an old body by a spiteful witch, her only chance of breaking the spell lies with a self-indulgent yet insecure young wizard and his companions in his legged, walking castle."
        )

        val mappedMovie = remoteMovie.toMovie()

        assertThat(mappedMovie).isEqualTo(movie)
    }

    @Test
    fun `toMovie() nullable remote movie to movie with default values`() {
        val remoteMovie = MoviesRemoteResponse.Movie(
            adult = null, backdropPath = null, movieId = null,
            originalLanguage = null, originalTitle = null,
            overview = null, popularity = null, posterPath = null,
            releaseDate = null, title = null, video = null,
            voteAverage = null, voteCount = null
        )

        val movie = Movie(
            id = -1, name = "",
            imageUrlSmall = "",
            imageUrlFull = "",
            description = "",
            voteAverage = 0.0,
            releaseDate = "",
            overview = ""
        )

        val mappedMovie = remoteMovie.toMovie()

        assertThat(mappedMovie).isEqualTo(movie)
    }
}