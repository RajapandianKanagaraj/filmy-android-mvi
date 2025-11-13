package com.android.filmy.model

import com.filmy.tracking.TrackingParam

data class CollectionDataModel(
    override val id: String,
    override val contentType: ContentType,
    override val trackingParam: TrackingParam = TrackingParam.Empty,
    val page: Int,
    val title: String,
    val feeds: List<SegmentDataModel>
): SegmentDataModel, Trackable {
    fun updateTitle(title: String): CollectionDataModel = copy(
        title = title,
        trackingParam = trackingParam.copy(
            metadata = mutableMapOf(
                "view_type" to "carousel",
                "id" to id,
                "title" to title,
                "num_of_items" to "${ feeds.size }",
            )
        )
    )
}