package com.android.filmy.model

import com.android.filmy.BuildConfig

data class MovieDataModel(
    override val id: String,
    override val contentType: ContentType = ContentType.MOVIE,
    val adult: Boolean,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val originalLanguage: String,
) : SegmentDataModel {

    val posterUrl : String
        get() = "${BuildConfig.BASE_IMAGE_URL}${posterPath}"
    val backdropUrl: String
        get() = "${BuildConfig.BASE_IMAGE_URL}${backdropPath}"
}