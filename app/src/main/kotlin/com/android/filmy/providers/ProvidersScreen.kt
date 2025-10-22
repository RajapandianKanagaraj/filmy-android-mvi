package com.android.filmy.providers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.android.filmy.analytics.InitRumView
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.mvi.UiAction

@Composable
fun ProvidersScreen() {
    val actionDispatcher = LocalActionDispatcher.current

    InitRumView(
        viewKey = "ProvidersScreen",
        viewName = "Providers",
        attributes = mapOf("screen_name" to "Providers")
    )

    LaunchedEffect(Unit) {
        actionDispatcher.dispatch(
            UiAction.ScreenViewed(
                "providers", mapOf("screen_name" to "providers")
            )
        )
    }
    Box(
        modifier = Modifier.fillMaxSize()

    ) {
        Text("Providers Screen")
    }
}