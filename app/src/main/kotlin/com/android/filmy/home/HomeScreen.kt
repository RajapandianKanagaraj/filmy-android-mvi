package com.android.filmy.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.filmy.analytics.InitRumView
import com.android.filmy.analytics.TrackingEvent
import com.android.filmy.core.SegmentData.MovieSegmentData
import com.android.filmy.core.SegmentState
import com.android.filmy.mvi.ActionDispatcher
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.ui.components.Carousel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val datadogTracker = viewModel.datadogTracker
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val actionDispatcher = LocalActionDispatcher.current

    InitRumView(
        viewKey = "HomeScreen",
        viewName = "Home",
        attributes = mapOf("screen_name" to "Home")
    )
    LaunchedEffect(Unit) {
        datadogTracker.trackPageEvent(TrackingEvent.SCREEN_VIEWED, mapOf("screen_name" to "Home"))
    }

    LazyColumn(modifier = modifier) {
        items(state) { segment ->
            RenderState(segment, actionDispatcher)
        }

//            LazyVerticalGrid(modifier = Modifier, columns = GridCells.Fixed(2)) {
//                items(state.movies, key = { it.id }) { movie ->
//                    MovieCard(
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .wrapContentSize(),
//                        title = movie.displayTitle,
//                        posterUrl = movie.posterUrl,
//                        onClick = {
//                            // Handle movie click
//                        }
//                    )
//                }
//            }
//        }
    }
}

@Composable
fun RenderState(
    segmentState: SegmentState,
    actionDispatcher: ActionDispatcher,
) {
    if (segmentState.isLoading) {
        SegmentLoading()
    } else if (segmentState.error != null) {
        SegmentError()
    } else {
        RenderSegment(
            segment = segmentState.segment as MovieSegmentData,
            actionDispatcher = actionDispatcher,
        )
    }
}

@Composable
fun RenderSegment(
    segment: MovieSegmentData,
    actionDispatcher: ActionDispatcher,
) {
    Carousel(
        id = segment.id,
        title = segment.title,
        actionDispatcher = actionDispatcher,
        items = segment.movies,
    )
}

@Composable
fun SegmentLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(8.dp)
    ) {
        Text("Loading...")
    }
}

@Composable
fun SegmentError() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(8.dp)
    ) {

        Text("Error")
    }
}