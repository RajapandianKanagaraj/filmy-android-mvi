package com.android.filmy.mvi

import com.android.filmy.model.Movie

data class HomeState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String? = null,
)