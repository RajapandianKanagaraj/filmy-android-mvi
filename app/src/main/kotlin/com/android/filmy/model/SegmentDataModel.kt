package com.android.filmy.model

interface SegmentDataModel {
    val id: String

    companion object {
        val Empty = object : SegmentDataModel {
            override val id: String = "none"
        }
    }
}
