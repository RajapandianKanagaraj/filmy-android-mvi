package com.android.filmy.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.ui.graphics.vector.ImageVector
import com.android.filmy.ui.navigation.destinations.HomeDestination
import com.android.filmy.ui.navigation.destinations.ProviderDestination

data class BottomNavTab<T: Any>(
    val root: T,
    val title: String,
    val icon: ImageVector,
)

fun <T: Any> BottomNavTab<T>.allTabs() = listOf(
    BottomNavTab(HomeDestination, "Home", Icons.Outlined.Home),
    BottomNavTab(ProviderDestination, "Providers", Icons.Outlined.Movie)
)
