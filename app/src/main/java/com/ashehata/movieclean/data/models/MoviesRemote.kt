package com.ashehata.movieclean.data.models

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class MoviesPopularResponse(
    @field:Json(name = "page")
    val page: Int? = 0, // 1
    @field:Json(name = "results")
    val movies: List<Movie>? = listOf(),
    @field:Json(name = "total_pages")
    val totalPages: Int? = 0, // 35413
    @field:Json(name = "total_results")
    val totalResults: Int? = 0 // 708260
) {
    @Keep
    data class Movie(
        @field:Json(name = "adult")
        val adult: Boolean? = false, // false
        @field:Json(name = "backdrop_path")
        val backdropPath: String? = "", // /5GA3vV1aWWHTSDO5eno8V5zDo8r.jpg
        @field:Json(name = "genre_ids")
        val genreIds: List<Int?>? = listOf(),
        @field:Json(name = "id")
        val id: Int? = 0, // 760161
        @field:Json(name = "original_language")
        val originalLanguage: String? = "", // en
        @field:Json(name = "original_title")
        val originalTitle: String? = "", // Orphan: First Kill
        @field:Json(name = "overview")
        val overview: String? = "", // After escaping from an Estonian psychiatric facility, Leena Klammer travels to America by impersonating Esther, the missing daughter of a wealthy family. But when her mask starts to slip, she is put against a mother who will protect her family from the murderous “child” at any cost.
        @field:Json(name = "popularity")
        val popularity: Double? = 0.0, // 4228.914
        @field:Json(name = "poster_path")
        val posterPath: String? = "", // /pHkKbIRoCe7zIFvqan9LFSaQAde.jpg
        @field:Json(name = "release_date")
        val releaseDate: String? = "", // 2022-07-27
        @field:Json(name = "title")
        val title: String? = "", // Orphan: First Kill
        @field:Json(name = "video")
        val video: Boolean? = false, // false
        @field:Json(name = "vote_average")
        val voteAverage: Double? = 0.0, // 6.9
        @field:Json(name = "vote_count")
        val voteCount: Int? = 0 // 948
    )
}
