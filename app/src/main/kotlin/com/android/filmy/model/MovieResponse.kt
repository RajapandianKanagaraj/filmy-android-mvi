package com.android.filmy.model

import com.android.filmy.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    val page: Int,
    @param:Json(name = "total_pages") val totalPages: Int,
    val results: List<Movie>,
    @param:Json(name = "total_results") val totalResults : Int,
)

data class Movie(
    val id: Int,
    val adult: Boolean,
    @param:Json(name = "backdrop_path") val backdrop_path: String?,
    val overview: String?,
    @param:Json(name = "poster_path") val posterPath: String?,
    @param:Json(name = "media_type") val mediaType: String?,
    @param:Json(name = "original_language") val originalLanguage: String?,
    val popularity: Double?,
    @param:Json(name = "vote_average") val voteAverage: Double?,
    @param:Json(name = "genre_ids") val genreIds: List<Int>,
    @param:Json(name = "vote_count") val voteCount: Int?,
    val title: String?,
    @param:Json(name = "original_title") val originalTitle: String?,
    @param:Json(name = "release_date") val releaseDate: String?,
    val video: Boolean?,
) {
    val displayTitle: String
        get() = title ?: originalTitle ?: "Untitled"

    val posterUrl : String
        get() = "${BuildConfig.BASE_IMAGE_URL}${posterPath}"
}
