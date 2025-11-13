package com.android.filmy.model

import com.android.filmy.BuildConfig
import com.filmy.tracking.TrackingParam

data class MovieDataModel(
    override val id: String,
    override val contentType: ContentType = ContentType.MOVIE,
    override val trackingParam: TrackingParam = TrackingParam.Empty,
    val adult: Boolean,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val originalLanguage: String,
) : SegmentDataModel, Trackable {

    val posterUrl : String
        get() = "${BuildConfig.BASE_IMAGE_URL}${posterPath}"
    val backdropUrl: String
        get() = "${BuildConfig.BASE_IMAGE_URL}${backdropPath}"
}