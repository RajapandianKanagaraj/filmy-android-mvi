package com.android.filmy.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.android.filmy.ui.navigation.destinations.HomeDestination
import com.android.filmy.ui.navigation.destinations.HomeTab
import com.android.filmy.ui.navigation.destinations.ProviderDestination
import com.android.filmy.ui.navigation.destinations.ProviderTab
import com.android.filmy.ui.navigation.homeGraph
import com.android.filmy.ui.navigation.providerGraph

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeTab,
    ) {
        navigation<HomeTab>(startDestination = HomeDestination) {
            homeGraph()
        }
        navigation<ProviderTab>(startDestination = ProviderDestination) {
            providerGraph()
        }
    }
}