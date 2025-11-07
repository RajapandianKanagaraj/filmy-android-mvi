package com.android.filmy.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.filmy.home.HomeScreen
import com.android.filmy.moviedetails.ContentDetailsScreen
import com.android.filmy.moviedetails.ContentDetailsViewModel
import com.android.filmy.providers.ProvidersScreen
import com.android.filmy.ui.navigation.destinations.HomeDestination
import com.android.filmy.ui.navigation.destinations.ContentDetailsDestination
import com.android.filmy.ui.navigation.destinations.ProviderDestination

fun NavGraphBuilder.homeGraph() {
    composable<HomeDestination> { HomeScreen() }
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