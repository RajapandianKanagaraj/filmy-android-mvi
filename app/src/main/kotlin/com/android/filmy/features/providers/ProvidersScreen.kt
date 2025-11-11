package com.android.filmy.features.providers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.filmy.analytics.InitRumView
import com.android.filmy.features.home.RenderSegment
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.mvi.UiAction
import com.android.filmy.ui.TrackableContent

@Composable
fun ProvidersScreen(
    modifier: Modifier = Modifier,
    viewModel: ProvidersViewModel = hiltViewModel(),
) {
//    val actionDispatcher = LocalActionDispatcher.current

    InitRumView(
        viewKey = "ProvidersScreen",
        viewName = "Providers",
        attributes = mapOf("screen_name" to "Providers")
    )

    val providerState = viewModel.providerState.collectAsState()
    val contentState = viewModel.contentState.collectAsState()

    TrackableContent(
        trackingParam = viewModel.getTrackingSubject()
    ) {
        LazyColumn(modifier = modifier) {
            item {
                RenderSegment(providerState.value.data)
            }

            items(contentState.value) { segment ->
                RenderSegment(segment = segment.data)
            }
        }
    }
}