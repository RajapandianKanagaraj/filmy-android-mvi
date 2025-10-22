package com.android.filmy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.filmy.mvi.ActionDispatcherImpl
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.ui.AppNavHost
import com.android.filmy.ui.AppTopBar
import com.android.filmy.ui.BottomNavBar
import com.android.filmy.ui.NavIconType
import com.android.filmy.ui.theme.FilmyAndroidMVITheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var actionDispatcher: ActionDispatcherImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmyAndroidMVITheme {
                val navController = rememberNavController()
                actionDispatcher.initialize(navController)

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val showBackArrow = remember(currentRoute) {
                    isNotTabRoot(currentRoute)
                }

                CompositionLocalProvider(LocalActionDispatcher provides actionDispatcher) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            AppTopBar(
                                navController = navController,
                                navIconType = if (showBackArrow) NavIconType.BACK else NavIconType.NONE,
                            )
                        },
                        bottomBar = {
                            BottomNavBar(navController = navController)
                        }
                    ) { innerPadding ->
                        AppNavHost(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }
}

private fun isNotTabRoot(currentRoute: String?) : Boolean {
    if (currentRoute == null) return false

    val tabRoots = listOf(
        "HomeTab",
        "ProviderTab",
        "HomeDestination",
        "ProviderDestination"
    )

    return !tabRoots.any { currentRoute.contains(it) } //!tabRoots.contains(currentRoute)
}
