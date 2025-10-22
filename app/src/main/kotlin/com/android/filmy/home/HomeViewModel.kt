package com.android.filmy.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.filmy.analytics.DatadogTracker
import com.android.filmy.core.MovieContentSegment
import com.android.filmy.core.SegmentRepository
import com.android.filmy.core.SegmentState
import com.android.filmy.data.MovieRepository
import com.android.filmy.mvi.HomeAction
import com.android.filmy.mvi.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val segmentRepository: SegmentRepository,
    val datadogTracker: DatadogTracker,
) : ViewModel() {
    val state: StateFlow<List<SegmentState>> = segmentRepository.segmentState
    private val actions = MutableSharedFlow<HomeAction>()

    init {
        viewModelScope.launch {
            fetchNowPlayingMovies()
        }
    }

    private suspend fun fetchNowPlayingMovies() {
        segmentRepository.getContentSegments(
            listOf(
                MovieContentSegment.NowPlayingMovies,
                MovieContentSegment.TrendingMovies,
                MovieContentSegment.UpcomingMovies,
                MovieContentSegment.PopularMovies,
            )
        )
    }
}