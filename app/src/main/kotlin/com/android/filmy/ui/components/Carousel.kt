package com.android.filmy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.android.filmy.model.Movie
import com.android.filmy.mvi.ActionDispatcher
import com.android.filmy.mvi.NavAction
import com.android.filmy.mvi.UiAction

@Composable
fun Carousel(
    id: String,
    title: String,
    items: List<Movie>,
    actionDispatcher: ActionDispatcher,
    modifier: Modifier = Modifier,
) {
    DisposableEffect(Unit) {
        actionDispatcher.dispatch(
            UiAction.ViewAppeared(
                "$title Carousel",
                mutableMapOf(
                    "view_type" to "carousel",
                    "id" to id,
                    "title" to title,
                    "num_of_items" to items.size,
                )
            )
        )
        onDispose {
            actionDispatcher.dispatch(
                UiAction.ViewDisappeared(
                    "$title Carousel",
                    mutableMapOf(
                        "view_type" to "carousel",
                        "id" to id,
                        "title" to title,
                        "num_of_items" to items.size,
                    )
                )
            )
        }
    }

    Column(modifier = modifier) {
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(12.dp)
        )

        LazyRow(modifier = Modifier) {
            items(items, key = { it.id }) { movie ->
                MovieCard(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize(),
                    id = movie.id,
                    title = movie.displayTitle,
                    posterUrl = movie.posterUrl,
                    ancestorId = id,
                    onClick = {
                        actionDispatcher.dispatch(
                            UiAction.ViewClicked(
                                "movie_card ${movie.id}",
                                mapOf(
                                    "ancestorId" to id,
                                    "movie_id" to movie.id,
                                    "movie_title" to movie.displayTitle,
                                    "poster_url" to movie.posterUrl,
                                )
                            )
                        )
                        actionDispatcher.dispatch(NavAction.navigateToMovieDetails(movie.id))
                    }
                )
            }
        }
    }
}