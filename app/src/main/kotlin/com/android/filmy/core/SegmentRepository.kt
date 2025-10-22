package com.android.filmy.core

import com.android.filmy.data.MovieRepository
import com.android.filmy.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SegmentRepository {
    val segmentState: StateFlow<List<SegmentState>>
    suspend fun getContentSegments(segments: List<ContentSegment>)
}

class SegmentRepositoryImpl @Inject constructor(
    val movieRepository: MovieRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : SegmentRepository {
    private val _segmentState = MutableStateFlow<List<SegmentState>>(emptyList())

    override val segmentState: StateFlow<List<SegmentState>>
        get() = _segmentState

    override suspend fun getContentSegments(segments: List<ContentSegment>) {
        withContext(ioDispatcher) {
            val startingIndex = _segmentState.value.size
            _segmentState.update { it + List(segments.size) { SegmentState.Loading} }

            segments.mapIndexed { index, segment ->
                async {
                    val resultState = try {
                        when (segment) {
                            is MovieContentSegment -> {
                                val movieSegment = movieRepository.getMovieSegment(segment)
                                SegmentState(
                                    isLoading = false,
                                    segment = movieSegment,
                                )
                            }

                            TvContentSegment.NowPlayingSeries -> SegmentState.Idle
                            TvContentSegment.PopularSeries -> SegmentState.Idle
                            TvContentSegment.TrendingSeries -> SegmentState.Idle
                            TvContentSegment.UpcomingSeries -> SegmentState.Idle
                        }
                    } catch (e: Exception) {
                        SegmentState(
                            isLoading = false,
                            error = e.message
                        )
                    }
                    withContext(Dispatchers.Main.immediate) {
                        _segmentState.update { currentList ->
                            currentList.toMutableList().apply {
                                this[startingIndex + index] = resultState
                            }
                        }
                    }
                }
            }
        }.awaitAll()
    }
}