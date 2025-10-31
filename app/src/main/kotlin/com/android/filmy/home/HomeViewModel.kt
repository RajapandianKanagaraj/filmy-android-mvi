package com.android.filmy.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.filmy.analytics.DatadogTracker
import com.android.filmy.core.Segment
import com.android.filmy.core.SegmentLCEState
import com.android.filmy.core.SegmentRepository
import com.android.filmy.model.ContentType
import com.android.filmy.ui.TopNavTab
import com.android.filmy.ui.TopNavTabs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val segmentRepository: SegmentRepository,
    val datadogTracker: DatadogTracker,
) : ViewModel() {

    private val selectedTab = MutableStateFlow(TopNavTabs.allTab)
    val state: StateFlow<List<SegmentLCEState>> = combine(
        segmentRepository.segmentState,
        selectedTab
    ) { allSegments, selectedTab ->
        when (selectedTab.route) {
            "movie-section" -> allSegments.filter {
                it.data?.contentType == ContentType.MOVIE
            }
            "tv-section" -> allSegments.filter {
                it.data?.contentType == ContentType.TV_SHOW
            }
            else -> allSegments
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    init {
        viewModelScope.launch {
            fetchSegments()
        }
    }

    suspend fun fetchSegments(segments: List<Segment> = HomeSegments.allSegments) {
        segmentRepository.getContentSegments(segments)
    }

    fun onTabSelected(tab: TopNavTab) {
        selectedTab.value = tab
    }

//    fun getMovieSegments(): List<SegmentLCEState> {
//        return _state.value.filter { segmentState ->
//            segmentState.data?.let { data ->
//                isMovieSegment(data)
//            } ?: false
//        }
//    }
//
//    fun getTvShowSegments(): List<SegmentLCEState> {
//        return _state.value.filter { segmentState ->
//            segmentState.data?.let { data ->
//                isTvShowSegment(data)
//            } ?: false
//        }
//    }
//
//    fun getAllSegments(): List<SegmentLCEState> {
//        return _state.value
//    }
//
//    private fun isMovieSegment(dataModel: SegmentDataModel): Boolean {
//        return dataModel is CollectionDataModel && dataModel.contentType?.let { contentType ->
//            contentType == ContentType.MOVIE
//        } ?: false
//    }
//
//    private fun isTvShowSegment(dataModel: SegmentDataModel): Boolean {
//        return dataModel is CollectionDataModel && dataModel.contentType?.let { contentType ->
//            contentType == ContentType.MOVIE
//        } ?: false
//    }
}