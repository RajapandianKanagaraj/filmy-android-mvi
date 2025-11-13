package com.android.filmy.parsers

import com.android.filmy.model.ContentType
import com.android.filmy.model.MovieDataModel
import com.android.filmy.model.SegmentDataModel
import com.android.filmy.model.response.MediaContent
import com.filmy.tracking.TrackingParam
import javax.inject.Inject

class MediaContentParser @Inject constructor(): ContentParser<MediaContent> {
    override fun parse(response: MediaContent, contentType: ContentType): SegmentDataModel {
        return MovieDataModel(
            id = response.id.toString(),
            title = response.displayTitle,
            contentType = contentType,
            trackingParam = TrackingParam(
                id = "movie_card:${response.id}",
                name = "movie_card",
                role = "Component",
                metadata = mutableMapOf(
                    "movie_id" to response.id.toString(),
                    "movie_title" to response.displayTitle,
                )
            ),
            adult = response.adult,
            overview = response.overview ?: "",
            voteCount = response.voteCount ?: 0,
            voteAverage = response.voteAverage ?: 0.0,
            posterPath = response.posterPath ?: "",
            releaseDate = response.releaseDate ?: "",
            backdropPath = response.backdropPath ?: "",
            originalLanguage = response.originalLanguage ?: "",
        )
    }
}