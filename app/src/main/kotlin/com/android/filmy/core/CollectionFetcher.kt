package com.android.filmy.core

import com.android.filmy.data.MovieRepository
import javax.inject.Inject
import com.android.filmy.core.Segment.CollectionSegment

class CollectionFetcher @Inject constructor(
    val movieRepository: MovieRepository,
): ContentFetcher<CollectionSegment> {
    override suspend fun fetchContent(segment: CollectionSegment): SegmentState {
        return when (segment.content) {
            is SegmentContent.MoviesSegment -> {
                val movieSegment = movieRepository.getMovieSegment(segment.content)
                SegmentState(
                    isLoading = false,
                    segment = movieSegment,
                )
            }
            is SegmentContent.TvShowsSegment -> {
                SegmentState.Idle
            }
            is SegmentContent.ActorsSegment -> {
                SegmentState.Idle
            }
        }
    }
}