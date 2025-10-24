package com.android.filmy.core

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
import kotlin.apply

interface SegmentRepository {
    val segmentState: StateFlow<List<SegmentState>>
    suspend fun getContentSegments(segments: List<Segment>)
}

class SegmentRepositoryImpl @Inject constructor(
    val collectionFetcher: CollectionFetcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : SegmentRepository {
    private val _segmentState = MutableStateFlow<List<SegmentState>>(emptyList())

    override val segmentState: StateFlow<List<SegmentState>>
        get() = _segmentState

    override suspend fun getContentSegments(segments: List<Segment>) {
        withContext(ioDispatcher) {
            val startingIndex = _segmentState.value.size
            _segmentState.update { it + List(segments.size) { SegmentState.Loading} }

            segments.mapIndexed { index, segment ->
                async {
                    val resultState = try {
                        when (segment) {
                            is Segment.CollectionSegment -> {
                                collectionFetcher.fetchContent(segment)
                            }
                            is Segment.BannerSegment -> {
                                SegmentState.Idle
                            }
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