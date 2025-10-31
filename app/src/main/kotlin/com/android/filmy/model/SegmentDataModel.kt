package com.android.filmy.model

interface SegmentDataModel {
    val id: String
    val contentType: ContentType

    companion object {
        val Empty = object : SegmentDataModel {
            override val id: String = "none"
            override val contentType: ContentType = ContentType.ALL
        }
    }
}

enum class ContentType {
    MOVIE,
    TV_SHOW,
    PERSON,
    ALL,
    PROVIDER,
}
