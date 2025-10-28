package com.android.filmy.parsers

import com.android.filmy.model.SegmentDataModel

interface ContentParser<in T> {
    fun parse(response: T): SegmentDataModel
}