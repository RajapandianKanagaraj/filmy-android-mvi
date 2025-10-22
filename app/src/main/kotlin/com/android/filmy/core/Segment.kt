package com.android.filmy.core

import com.android.filmy.model.Movie

sealed interface Segment {
    object EmptySegment: Segment
    data class MovieSegment(
        val title: String,
        val movies: List<Movie>
    ): Segment
}