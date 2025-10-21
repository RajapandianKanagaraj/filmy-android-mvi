package com.android.filmy.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.filmy.ui.navigation.destinations.HomeDestination
import com.android.filmy.ui.navigation.destinations.ProviderDestination

@Composable
fun BottomNavBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val tabs = listOf(
        BottomNavTab(HomeDestination, "Home", Icons.Outlined.Home),
        BottomNavTab(ProviderDestination, "Providers", Icons.Outlined.Movie)
    )

    NavigationBar {
        tabs.forEach { tab ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title,
                    )
                },
                label = { Text(tab.title) },
                selected = currentDestination?.isTabSelected(tab) == true,
                onClick = {
                    navController.navigate(tab.root) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun NavDestination?.isTabSelected(tab: BottomNavTab<*>): Boolean {
    return this?.hierarchy?.any {
        it.route?.contains(tab.root::class.simpleName.orEmpty()) == true
    } == true
}