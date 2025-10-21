package com.android.filmy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.android.filmy.model.Movie
import com.android.filmy.mvi.ActionDispatcher
import com.android.filmy.mvi.NavAction

@Composable
fun Carousel(
    title: String,
    items: List<Movie>,
    actionDispatcher: ActionDispatcher,
    modifier: Modifier = Modifier,
) {
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
                    title = movie.displayTitle,
                    posterUrl = movie.posterUrl,
                    onClick = {
                        actionDispatcher.dispatch(NavAction.navigateToMovieDetails(movie.id))
                    }
                )
            }
        }
    }
}