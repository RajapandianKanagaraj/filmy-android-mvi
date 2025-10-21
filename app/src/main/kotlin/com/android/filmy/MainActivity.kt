package com.android.filmy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.android.filmy.mvi.ActionDispatcher
import com.android.filmy.mvi.ActionDispatcherImpl
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.ui.AppNavHost
import com.android.filmy.ui.BottomNavBar
import com.android.filmy.ui.theme.FilmyAndroidMVITheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var actionDispatcher: ActionDispatcherImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            FilmyAndroidMVITheme {
                val navController = rememberNavController()
                actionDispatcher.initialize(navController)
                CompositionLocalProvider(LocalActionDispatcher provides actionDispatcher) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
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
