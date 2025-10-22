package com.android.filmy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.mvi.UiAction

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    id: Int,
    title: String,
    posterUrl: String,
    ancestorId: String? = null,
    onClick: () -> Unit
) {
    val actionDispatcher = LocalActionDispatcher.current
    LaunchedEffect(Unit) {
        actionDispatcher.dispatch(
            UiAction.ViewAppeared(
                "movie_card_$id",
                mutableMapOf(
                    "ancestorId" to ancestorId.orEmpty(),
                    "movie_id" to id,
                    "movie_title" to title,
                    "poster_url" to posterUrl,
                )
            )
        )
    }
    Card(
        onClick = onClick,
        modifier = modifier
            .width(160.dp)
            .aspectRatio(2f / 3f)
            .semantics {
                contentDescription = title
                role = Role.Button
            },
    ) {
        Column {
            AsyncImage(
                model = posterUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}