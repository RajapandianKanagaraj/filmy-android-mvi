package com.android.filmy.mvi

sealed interface Action

sealed interface NavAction: Action {
    data object navigateBack: NavAction
    data class navigateToTab(val tabRoute: Any): NavAction
    data class navigateToMovieDetails(val movieId: Int): NavAction
}

sealed interface UiAction: Action {
    data class ViewAppeared(val viewName: String, val attributes: Map<String, Any>): UiAction
    data class ViewDisappeared(val viewName: String, val attributes: Map<String, Any>): UiAction
    data class ScreenViewed(val screenName: String, val attributes: Map<String, Any>): UiAction
    data class ViewClicked(val viewName: String, val attributes: Map<String, Any>): UiAction
}

