package com.android.filmy.features.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.android.filmy.analytics.tracking.TrackingParam
import com.android.filmy.core.SegmentLCEState
import com.android.filmy.core.SegmentRepository
import com.android.filmy.ui.navigation.destinations.ContentDetailsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ContentDetailsViewModel @Inject constructor(
    val segmentRepository: SegmentRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val args = savedStateHandle.toRoute<ContentDetailsDestination>()
    val contentId = args.contentId
    val contentType = args.contentType

    val state: StateFlow<List<SegmentLCEState>> = segmentRepository.segmentState

    init {
        viewModelScope.launch {
            segmentRepository.getContentSegments(
                ContentDetailsSegments.getSegment(contentId, contentType.name),
            )
        }
    }

    fun getTrackingParams() = TrackingParam(
        id = "detail_screen:${UUID.randomUUID()}",
        name = "detail_screen",
        role = "screen",
        metadata = mapOf("screen_name" to "ContentDetailsScreen"),
    )
}