package com.android.filmy.parsers

import com.android.filmy.model.ContentType
import com.android.filmy.model.SegmentDataModel
import com.android.filmy.model.response.FeedItemResponse
import com.android.filmy.model.response.MediaContent
import com.android.filmy.model.response.Person
import javax.inject.Inject

class TrendingContentParser @Inject constructor(
    val movieParser: MediaContentParser,
    val personParser: PersonParser
) : ContentParser<FeedItemResponse> {
    override fun parse(response: FeedItemResponse, contentType: ContentType): SegmentDataModel {
        return when (response) {
            is MediaContent -> movieParser.parse(response, ContentType.MOVIE)
            is Person -> personParser.parse(response, ContentType.PERSON)
            else -> SegmentDataModel.Empty
        }
    }
}