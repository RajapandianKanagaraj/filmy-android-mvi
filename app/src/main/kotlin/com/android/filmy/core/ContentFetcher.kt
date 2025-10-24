package com.android.filmy.core

interface ContentFetcher<T: Segment> {
    suspend fun fetchContent(segment: T): SegmentState
}