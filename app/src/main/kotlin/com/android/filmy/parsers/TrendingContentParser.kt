package com.android.filmy.parsers

import com.android.filmy.model.ContentType
import com.android.filmy.model.MovieDataModel
import com.android.filmy.model.PersonDataModel
import com.android.filmy.model.SegmentDataModel
import com.android.filmy.model.response.FeedItemResponse
import com.android.filmy.model.response.MediaContent
import com.android.filmy.model.response.Person
import javax.inject.Inject

class TrendingContentParser @Inject constructor() : ContentParser<FeedItemResponse> {
    override fun parse(response: FeedItemResponse, contentType: ContentType): SegmentDataModel {
        return when (response.mediaType) {
            "movie", "tv" -> {
                response as MediaContent
                MovieDataModel(
                    id = response.id.toString(),
                    title = response.displayTitle,
                    adult = response.adult,
                    contentType = contentType,
                    overview = response.overview ?: "",
                    voteCount = response.voteCount ?: 0,
                    voteAverage = response.voteAverage ?: 0.0,
                    posterPath = response.posterPath ?: "",
                    releaseDate = response.releaseDate ?: "",
                    backdropPath = response.backdropPath ?: "",
                    originalLanguage = response.originalLanguage ?: "",
                )
            }

            "person" -> {
               response as Person
                PersonDataModel(
                    id = response.id.toString(),
                    name = response.name,
                    adult = response.adult,
                    gender = response.gender,
                    popularity = response.popularity,
                    knownForDepartment = response.knownForDepartment.orEmpty(),
                    profilePath = response.profilePath ?: "",
                    knownFor = response.knownFor ?: emptyList(),
                )
            }

            else -> SegmentDataModel.Empty
        }
    }
}