package com.android.filmy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.filmy.analytics.tracking.LocalTrackingContext
import com.android.filmy.analytics.tracking.TrackingContext
import com.android.filmy.mvi.ActionDispatcher
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.ui.AppScaffold
import com.android.filmy.ui.theme.FilmyAndroidMVITheme
import com.datadog.android.rum.GlobalRumMonitor
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var actionDispatcher: ActionDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalRumMonitor.get().startView(
            key = this,
            name = "MainActivity",
            attributes = emptyMap()
        )

        setContent {
            FilmyAndroidMVITheme {
                val navController = rememberNavController()
                actionDispatcher.setNavController(navController)

//                NavigationViewTrackingEffect(
//                    navController = navController,
//                    trackArguments = true,
//                )

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val showBackArrow = remember(currentRoute) {
                    isNotTabRoot(currentRoute)
                }

                val id = "main_activity:${UUID.randomUUID()}"
                val name = "main_activity"

                val trackingContext = TrackingContext(
                    id = id,
                    name = name,
                    ancestorChain = id, // To get just immediate parent in the Ancestry Chain
//            ancestorChain = trackingSubject.ancestorChain, // To get full ancestry chain
                )

                CompositionLocalProvider(
                    LocalActionDispatcher provides actionDispatcher,
                    LocalTrackingContext provides trackingContext,
                ) {
                    AppScaffold(
                        navController = navController,
                        showBackArrow = showBackArrow,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        GlobalRumMonitor.get().stopView(this)
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
