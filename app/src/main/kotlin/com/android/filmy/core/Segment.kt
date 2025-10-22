package com.android.filmy.core

import com.android.filmy.model.Movie
import java.util.UUID

sealed interface Segment {
    object EmptySegment: Segment
    data class MovieSegment(
        val id: String = UUID.randomUUID().toString(),
        val title: String,
        val movies: List<Movie>
    ): Segment
}