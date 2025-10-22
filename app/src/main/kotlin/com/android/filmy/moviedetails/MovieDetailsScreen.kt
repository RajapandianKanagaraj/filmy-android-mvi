package com.android.filmy.moviedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.filmy.analytics.InitRumView
import com.android.filmy.analytics.TrackingEvent
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.mvi.UiAction

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    modifier: Modifier = Modifier,
) {
    val actionDispatcher = LocalActionDispatcher.current

    InitRumView(
        viewKey = "MovieDetailsScreen",
        viewName = "MovieDetails",
        attributes = mapOf("screen_name" to "MovieDetails")
    )

    LaunchedEffect(Unit) {
        actionDispatcher.dispatch(
            UiAction.ScreenViewed(
                "movie_detail", mapOf(
                    "screen_name" to "movie_detail",
                    "movieId" to movieId.toString(),
                )
            )
        )
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text("Movie Details Screen $movieId")
    }
}