package com.android.filmy.mvi

import androidx.navigation.NavController
import com.android.filmy.ui.navigation.destinations.MovieDetailsDestination
import javax.inject.Inject

interface ActionDispatcher {
    fun dispatch(action: Action)
}

class ActionDispatcherImpl @Inject constructor(): ActionDispatcher {
    private var navController: NavController? = null
    fun initialize(navController: NavController) {
        this.navController = navController
    }

    override fun dispatch(action: Action) {
        when(action) {
            NavAction.navigateBack -> navController?.popBackStack()
            is NavAction.navigateToMovieDetails -> navController?.navigate(MovieDetailsDestination(action.movieId))
            is NavAction.navigateToTab -> navController?.navigate(action.tabRoute)
        }
    }
}

class NoOpActionDispatcher: ActionDispatcher {
    override fun dispatch(action: Action) {
       // No-op
    }
}