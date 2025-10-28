package com.android.filmy.core

import com.android.filmy.model.CollectionDataModel
import com.android.filmy.model.response.FeedItemResponse
import com.android.filmy.model.response.PaginatedResponse
import com.android.filmy.parsers.ContentParser
import javax.inject.Inject

class PaginatedCollectionParser @Inject constructor() {
    fun<T: FeedItemResponse> parse(
        response: PaginatedResponse<T>,
        itemsParser: ContentParser<T>
    ): CollectionDataModel {
        return CollectionDataModel(
            page = response.page,
            title = "",
            feeds = response.results.map {
                itemsParser.parse(it)
            }
        )
    }
}