package com.android.filmy.moviedetails

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.android.filmy.analytics.InitRumView
import com.android.filmy.home.RenderState
import com.android.filmy.ui.TrackableContent

@Composable
fun ContentDetailsScreen(
    viewModel: ContentDetailsViewModel,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.state.collectAsState()

    InitRumView(
        viewKey = "MovieDetailsScreen",
        viewName = "MovieDetails",
        attributes = mapOf("screen_name" to "MovieDetails")
    )

//    LaunchedEffect(Unit) {
//        actionDispatcher.dispatch(
//            UiAction.ScreenViewed(
//                "movie_detail", mapOf(
//                    "screen_name" to "movie_detail",
//                    "movieId" to viewModel.contentId,
//                    "contentType" to viewModel.contentType.name.lowercase(),
//                )
//            )
//        )
//    }

    TrackableContent(
        trackingParam = viewModel.getTrackingParams(),
    ) {
        LazyColumn(modifier = modifier) {
            items(state.value) { segment ->
                RenderState(segment)
            }
        }
    }
}