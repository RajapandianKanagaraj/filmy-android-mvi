package com.android.filmy.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.android.filmy.home.HomeScreen
import com.android.filmy.moviedetails.MovieDetailsScreen
import com.android.filmy.providers.ProvidersScreen
import com.android.filmy.ui.navigation.destinations.HomeDestination
import com.android.filmy.ui.navigation.destinations.MovieDetailsDestination
import com.android.filmy.ui.navigation.destinations.ProviderDestination

fun NavGraphBuilder.homeGraph() {
    composable<HomeDestination> { HomeScreen() }
    composable<MovieDetailsDestination> { backStackEntry ->
        val movieId = backStackEntry.toRoute<MovieDetailsDestination>().movieId
        MovieDetailsScreen(movieId = movieId)
    }
}

fun NavGraphBuilder.providerGraph() {
    composable<ProviderDestination> { ProvidersScreen() }
    composable<MovieDetailsDestination> { backStackEntry ->
        val movieId = backStackEntry.toRoute<MovieDetailsDestination>().movieId
        MovieDetailsScreen(movieId = movieId)
    }
}