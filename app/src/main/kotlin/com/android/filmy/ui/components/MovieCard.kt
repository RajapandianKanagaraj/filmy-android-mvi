package com.android.filmy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.android.filmy.analytics.tracking.LocalTrackingContext
import com.android.filmy.analytics.tracking.TrackingContext
import com.android.filmy.analytics.tracking.TrackingSubject
import com.android.filmy.model.MovieDataModel
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.mvi.NavAction
import com.android.filmy.mvi.UiAction
import com.android.filmy.ui.TrackableContent
import com.android.filmy.ui.dispatchOnAppear

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    dataModel: MovieDataModel,
) {

//    Solution #1
    TrackableMovieCard(
        modifier = modifier,
        dataModel = dataModel,
    )

//    Solution #2
//    CustomModifierMovieCard(
//        modifier = modifier,
//        dataModel = dataModel,
//    )
}


// Solution #1 - Using Compose Wrapper
@Composable
fun TrackableMovieCard(
    modifier: Modifier = Modifier,
    dataModel: MovieDataModel,
) {
    val actionDispatcher = LocalActionDispatcher.current
    TrackableContent(
        trackingParam = dataModel.trackingParam
    ) {
        val parentContext = LocalTrackingContext.current
        val trackingParam = remember {
            dataModel.trackingParam
        }

        Card(
            onClick = {
                actionDispatcher.dispatch(UiAction.onViewClicked(trackingParam, parentContext))
                actionDispatcher.dispatch(
                    NavAction.navigateToContentDetails(
                        dataModel.id,
                        contentType = dataModel.contentType
                    )
                )
            },
            modifier = modifier
                .width(160.dp)
                .aspectRatio(2f / 3f)
                .semantics {
                    contentDescription = dataModel.title
                    role = Role.Button
                }
        ) {
            Column {
                AsyncImage(
                    model = dataModel.posterUrl,
                    contentDescription = dataModel.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}

// Solution #2 - Using Custom Modifier to dispatch the Tracking Events
//@Composable
//fun CustomModifierMovieCard(
//    modifier: Modifier = Modifier,
//    dataModel: MovieDataModel,
//) {
//    val actionDispatcher = LocalActionDispatcher.current
//    val localTrackingContext = LocalTrackingContext.current
//
//    val trackingParams = remember {
//        dataModel.trackingParam
//    }
//    val trackingSubject = remember {
//        TrackingSubject(
//            id = trackingParams.id,
//            name = trackingParams.name,
//            role = trackingParams.role,
//            metadata = trackingParams.metadata,
//            parentId = localTrackingContext?.id.orEmpty(),
//            parentName = localTrackingContext?.name.orEmpty(),
//        ).copy(
//            ancestorChain = localTrackingContext?.ancestorChain.orEmpty() + ":" + trackingParams.id
//        )
//    }
//
//    val newTrackingContext = remember(trackingSubject.id, trackingSubject.name) {
//        TrackingContext(
//            id = trackingSubject.id,
//            name = trackingSubject.name,
//            ancestorChain = trackingSubject.ancestorChain,
//        )
//    }
//
//    CompositionLocalProvider(LocalTrackingContext provides newTrackingContext) {
//        Card(
//            onClick = {
//                actionDispatcher.dispatch(UiAction.onViewClicked(trackingSubject))
//                actionDispatcher.dispatch(
//                    NavAction.navigateToContentDetails(
//                        dataModel.id,
//                        contentType = dataModel.contentType
//                    )
//                )
//            },
//            modifier = modifier
//                .width(160.dp)
//                .aspectRatio(2f / 3f)
//                .semantics {
//                    contentDescription = dataModel.title
//                    role = Role.Button
//                }
//                .dispatchOnAppear(trackingSubject = trackingSubject),
//        ) {
//            Column {
//                AsyncImage(
//                    model = dataModel.posterUrl,
//                    contentDescription = dataModel.title,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .weight(1f)
//                )
//            }
//        }
//    }
//}