package com.android.filmy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.android.filmy.model.PersonDataModel
import com.android.filmy.ui.TrackableContent

@Composable
fun PersonCard(
    modifier: Modifier = Modifier,
    dataModel: PersonDataModel,
    onClick: () -> Unit,
) {
    TrackableContent(
        trackingParam = dataModel.trackingParam,
    ) {
        Card(
            onClick = onClick,
            modifier = modifier
                .width(160.dp)
                .aspectRatio(2f / 3f)
                .semantics {
                    contentDescription = dataModel.name
                    role = Role.Button
                },
        ) {
            Column {
                AsyncImage(
                    model = dataModel.profileUrl,
                    contentDescription = dataModel.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}