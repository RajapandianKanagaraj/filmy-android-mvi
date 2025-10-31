package com.android.filmy.core

import com.android.filmy.model.CollectionDataModel
import com.android.filmy.model.ContentType
import com.android.filmy.model.response.FeedItemResponse
import com.android.filmy.model.response.PaginatedResponse
import com.android.filmy.parsers.ContentParser
import javax.inject.Inject

class PaginatedCollectionParser @Inject constructor() {
    fun<T: FeedItemResponse> parse(
        response: PaginatedResponse<T>,
        itemsParser: ContentParser<T>,
        contentType: ContentType,
    ): CollectionDataModel {
        return CollectionDataModel(
            page = response.page ?: 1,
            title = "",
            contentType = contentType,
            feeds = response.results.map {
                itemsParser.parse(it)
            }
        )
    }
}