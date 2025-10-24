package com.android.filmy.model

import java.util.UUID

data class CollectionDataModel(
    override val id: String = UUID.randomUUID().toString(),
    val page: Int,
    val title: String,
    val feeds: List<SegmentDataModel>
): SegmentDataModel {
    fun updateTitle(title: String): CollectionDataModel = copy(
        title = title
    )
}