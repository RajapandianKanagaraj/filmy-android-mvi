package com.android.filmy.model.response

import com.android.filmy.BuildConfig
import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class PaginatedResponse<out T: FeedItemResponse>(
    val page: Int? = 1,
    @param: Json(name = "total_pages") val totalPages: Int? = 1,
    @param: Json(name = "total_results") val totalResults: Int? = 10,
    val results: List<T>
): DataResponse

@Serializable
sealed interface FeedItemResponse: DataResponse {
    val id: Int
    val mediaType: String?
}

@Serializable
@SerialName("person")
data class Person(
    override val id: Int,
    val name: String,
    val gender: Int,
    val adult: Boolean,
    @param:Json(name = "media_type") override val mediaType: String? = "person",
    @param:Json(name = "known_for") val knownFor: List<MediaContent>?,
    @param:Json(name = "profile_path") val profilePath: String?,
    val popularity: Double,
    @param:Json(name = "known_for_department") val knownForDepartment: String?,
): FeedItemResponse {
    val profileUrl : String
        get() = "${BuildConfig.BASE_IMAGE_URL}${profilePath}"
}

@Serializable
@SerialName("media")
data class MediaContent(
    override val id: Int,
    val adult: Boolean,
    @param:Json(name = "backdrop_path") val backdropPath: String?,
    val overview: String?,
    @param:Json(name = "poster_path") val posterPath: String?,
    @param:Json(name = "media_type") override val mediaType: String?,
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
): FeedItemResponse {
    val displayTitle: String
        get() = title ?: originalTitle ?: name ?: originalName ?: "Untitled"

    val posterUrl : String
        get() = "${BuildConfig.BASE_IMAGE_URL}${posterPath}"
}

@Serializable
@SerialName("provider")
data class Provider(
    @param:Json(name = "provider_id") override val id: Int,
    override val mediaType: String? = "provider",
    @param:Json(name = "display_priority") val displayPriority: Int,
    @param:Json(name = "logo_path") val logoPath: String,
    @param:Json(name = "provider_name") val providerName: String,
): FeedItemResponse {
    val logoUrl : String
        get() = "${BuildConfig.BASE_IMAGE_URL}${logoPath}"
}
