package com.android.filmy.parsers

import com.android.filmy.model.MovieDataModel
import com.android.filmy.model.SegmentDataModel
import com.android.filmy.model.response.MediaContent
import javax.inject.Inject

class MediaContentParser @Inject constructor(): ContentParser<MediaContent> {
    override fun parse(response: MediaContent): SegmentDataModel {
        return MovieDataModel(
            id = response.id.toString(),
            title = response.displayTitle,
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