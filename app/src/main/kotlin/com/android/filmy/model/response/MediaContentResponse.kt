package com.android.filmy.model.response

import com.android.filmy.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediaContentResponse(
    val page: Int,
    @param:Json(name = "total_pages") val totalPages: Int,
    val results: List<MediaContent>,
    @param:Json(name = "total_results") val totalResults : Int,
): DataResponse

data class Person(
    val id: Int,
    val name: String,
    val gender: Int,
    val adult: Boolean,
    @param:Json(name = "known_for") val knownFor: List<MediaContent>?,
    @param:Json(name = "profile_path") val profilePath: String?,
    val popularity: Double,
    @param:Json(name = "known_for_department") val knownForDepartment: String?,
)

data class MediaContent(
    val id: Int,
    val adult: Boolean,
    @param:Json(name = "backdrop_path") val backdropPath: String?,
    val overview: String?,
    @param:Json(name = "poster_path") val posterPath: String?,
    @param:Json(name = "media_type") val mediaType: String?,
    @param:Json(name = "original_language") val originalLanguage: String?,
    val popularity: Double?,
    @param:Json(name = "vote_average") val voteAverage: Double?,
    @param:Json(name = "genre_ids") val genreIds: List<Int>,
    @param:Json(name = "vote_count") val voteCount: Int?,
    val title: String?,
    val name: String?,
    @param:Json(name = "original_title") val originalTitle: String?,
    @param:Json(name = "original_name") val originalName: String?,
    @param:Json(name = "release_date") val releaseDate: String?,
    @param:Json(name = "first_air_date") val firstAirData: String?,
    val video: Boolean?,
): DataResponse {
    val displayTitle: String
        get() = title ?: originalTitle ?: name ?: originalName ?: "Untitled"

    val posterUrl : String
        get() = "${BuildConfig.BASE_IMAGE_URL}${posterPath}"
}
