package com.android.filmy.core

import com.android.filmy.analytics.tracking.TrackingParam
import com.android.filmy.model.CollectionDataModel
import com.android.filmy.model.ContentType
import com.android.filmy.model.ProviderDataModel
import com.android.filmy.model.response.FeedItemResponse
import com.android.filmy.model.response.PaginatedResponse
import com.android.filmy.parsers.ContentParser
import java.util.UUID
import javax.inject.Inject

class PaginatedCollectionParser @Inject constructor() {
    fun <T : FeedItemResponse> parse(
        response: PaginatedResponse<T>,
        itemsParser: ContentParser<T>,
        contentType: ContentType,
    ): CollectionDataModel {
        val id = UUID.randomUUID().toString()
        return CollectionDataModel(
            id = id,
            page = response.page ?: 1,
            title = "",
            contentType = contentType,
            trackingParam = TrackingParam(
                id = "carousel:$id",
                name = "carousel",
                role = "Component",
            ),
            feeds = response.results
                .map { itemsParser.parse(it, contentType) }
                .let { it ->
                    val sortedProviders = it
                        .filterIsInstance<ProviderDataModel>()
                        .sortedBy { provider -> provider.displayPriority }
                        .take(20)

                    val otherItems = it.filterNot { item -> item is ProviderDataModel }
                    sortedProviders + otherItems
                }
        )
    }
}