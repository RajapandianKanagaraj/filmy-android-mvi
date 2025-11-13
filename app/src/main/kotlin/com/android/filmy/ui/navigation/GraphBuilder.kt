package com.android.filmy.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.filmy.features.home.HomeScreen
import com.android.filmy.features.home.OldHomeScreen
import com.android.filmy.features.home.Trackable_HomeScreen
import com.android.filmy.features.moviedetails.ContentDetailsScreen
import com.android.filmy.features.moviedetails.ContentDetailsViewModel
import com.android.filmy.features.providers.ProvidersScreen
import com.android.filmy.ui.navigation.destinations.HomeDestination
import com.android.filmy.ui.navigation.destinations.ContentDetailsDestination
import com.android.filmy.ui.navigation.destinations.ProviderDestination

fun NavGraphBuilder.homeGraph() {
    composable<HomeDestination> { Trackable_HomeScreen() }
    composable<ContentDetailsDestination> { backStackEntry ->
        val viewModel: ContentDetailsViewModel = hiltViewModel(backStackEntry)
        ContentDetailsScreen(viewModel = viewModel)
    }
}

fun NavGraphBuilder.providerGraph() {
    composable<ProviderDestination> { ProvidersScreen() }
    composable<ContentDetailsDestination> { backStackEntry ->
        val viewModel: ContentDetailsViewModel = hiltViewModel(backStackEntry)
        ContentDetailsScreen(viewModel = viewModel)
    }
}