package com.android.filmy.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.ui.components.Carousel
import com.android.filmy.ui.components.MovieCard

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val actionDispatcher = LocalActionDispatcher.current

    LazyColumn(modifier = modifier) {
        item {
            Carousel(
                title = "Popular Movies",
                actionDispatcher = actionDispatcher,
                items = state.movies
            )

            Carousel(
                title = "Trending Movies",
                actionDispatcher = actionDispatcher,
                items = state.movies,
            )

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
        }
    }
}