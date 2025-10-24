package com.android.filmy.parsers

import com.android.filmy.model.SegmentDataModel

interface ContentParser<T> {
    fun parse(response: T): SegmentDataModel
}