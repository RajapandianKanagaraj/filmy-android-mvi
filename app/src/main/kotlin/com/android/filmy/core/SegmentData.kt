package com.android.filmy.core

import com.android.filmy.model.Movie
import java.util.UUID

sealed interface SegmentData {
    object EmptySegment: SegmentData
    data class MovieSegmentData(
        val id: String = UUID.randomUUID().toString(),
        val title: String,
        val movies: List<Movie>
    ): SegmentData
}