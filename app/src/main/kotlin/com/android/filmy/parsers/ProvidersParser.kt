package com.android.filmy.parsers

import com.filmy.tracking.TrackingParam
import com.android.filmy.model.ContentType
import com.android.filmy.model.ProviderDataModel
import com.android.filmy.model.SegmentDataModel
import com.android.filmy.model.response.Provider
import java.util.UUID
import javax.inject.Inject

class ProvidersParser @Inject constructor(): ContentParser<Provider> {
    override fun parse(response: Provider, contentType: ContentType): SegmentDataModel {
        return ProviderDataModel(
            id = response.id.toString(),
            providerName = response.providerName,
            contentType = contentType,
            logoPath = response.logoPath,
            logoUrl = response.logoUrl,
            displayPriority = response.displayPriority,
            trackingParam = TrackingParam(
                id = "provider_card:${UUID.randomUUID()}",
                name = "provider_card",
                role = "Component",
                metadata = mutableMapOf(
                    "provider_id" to response.id.toString(),
                    "provider_name" to response.providerName,
                    "display_priority" to response.displayPriority.toString(),
                )
            )
        )
    }
}