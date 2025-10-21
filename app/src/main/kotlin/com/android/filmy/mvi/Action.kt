package com.android.filmy.mvi

sealed interface Action

sealed interface NavAction: Action {
    data object navigateBack: NavAction
    data class navigateToTab(val tabRoute: Any): NavAction
    data class navigateToMovieDetails(val movieId: Int): NavAction
}

