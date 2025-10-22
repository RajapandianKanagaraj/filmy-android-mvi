package com.android.filmy.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.datadog.android.rum.GlobalRumMonitor

@Composable
fun InitRumView(
    viewKey: String,
    viewName: String,
    attributes: Map<String, Any> = emptyMap(),
) {
    DisposableEffect(Unit) {
        val rumMonitor = GlobalRumMonitor.get()

        rumMonitor.startView(
            key = viewKey,
            name = viewName,
            attributes = attributes,
        )
        onDispose {
            rumMonitor.stopView(viewKey)
        }
    }
}