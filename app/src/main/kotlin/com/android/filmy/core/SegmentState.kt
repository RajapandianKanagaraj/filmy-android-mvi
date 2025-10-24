package com.android.filmy.core

data class SegmentState(
    val isLoading: Boolean = false,
    val segment: SegmentData = SegmentData.EmptySegment,
    val error: String? = null,
) {

    companion object {
        val Idle = SegmentState()
        val Loading = SegmentState(isLoading = true)
    }
}

