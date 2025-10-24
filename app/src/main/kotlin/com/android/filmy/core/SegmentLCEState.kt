package com.android.filmy.core

import com.android.filmy.model.SegmentDataModel

data class SegmentLCEState(
    val data: SegmentDataModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    companion object {
        val Idle = SegmentLCEState()
        val Loading = SegmentLCEState(isLoading = true)
    }
}
