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
import com.android.filmy.model.CollectionDataModel
import com.android.filmy.model.MovieDataModel
import com.android.filmy.model.PersonDataModel
import com.android.filmy.model.ProviderDataModel
import com.android.filmy.model.SegmentDataModel
import com.android.filmy.ui.TrackableContent

@Composable
fun <T : SegmentDataModel> Carousel(
    dataModel: CollectionDataModel,
    items: List<T>,
    modifier: Modifier = Modifier,
) {

//    Solution #1
    TrackableCarousel(
        dataModel = dataModel,
        items = items,
        modifier = modifier,
    )

//    Solution #2

//    CustomModifierCarousel(
//        dataModel = dataModel,
//        items = items,
//        modifier = modifier,
//    )
}

// Solution #1 - Using Compose Wrapper
@Composable
private fun <T : SegmentDataModel> TrackableCarousel(
    dataModel: CollectionDataModel,
    items: List<T>,
    modifier: Modifier = Modifier,
) {
    TrackableContent(
        trackingParam = dataModel.trackingParam
    ) {
        Column(
            modifier = modifier
        ) {
            Text(
                text = dataModel.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )

            LazyRow(modifier = Modifier) {
                items(items, key = { it.id }) { item ->
                    CollectionItemResolver(item = item)
                }
            }
        }
    }
}

// Solution #2 - Using Custom Modifier to dispatch the Tracking Events
//@Composable
//private fun <T : SegmentDataModel> CustomModifierCarousel(
//    dataModel: CollectionDataModel,
//    items: List<T>,
//    modifier: Modifier = Modifier,
//) {
//    val localTrackingContext = LocalTrackingContext.current
//
//    val trackingParam = remember {
//        dataModel.trackingParam
//    }
//    val trackingSubject = remember {
//        TrackingSubject(
//            id = trackingParam.id,
//            name = trackingParam.name,
//            role = trackingParam.role,
//            metadata = trackingParam.metadata,
//            parentId = localTrackingContext?.id.orEmpty(),
//            parentName = localTrackingContext?.name.orEmpty(),
//            ancestorChain = localTrackingContext?.ancestorChain.orEmpty() + ":" + trackingParam.id,
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
//        Column(
//            modifier = modifier
//                .dispatchOnAppear(trackingSubject = trackingSubject)
//        ) {
//            Text(
//                text = dataModel.title,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis,
//                style = MaterialTheme.typography.titleMedium,
//                modifier = Modifier.padding(12.dp)
//            )
//
//            LazyRow(modifier = Modifier) {
//                items(items, key = { it.id }) { item ->
//                    CollectionItemResolver(item = item)
//                }
//            }
//        }
//    }
//}

@Composable
private fun <T : SegmentDataModel> CollectionItemResolver(item: T) {
    when (item) {
        is MovieDataModel -> {
            MovieCard(
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize(),
                dataModel = item,
            )
        }

        is PersonDataModel -> {
            PersonCard(
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize(),
                dataModel = item,
                onClick = { }
            )
        }

        is ProviderDataModel -> {
            ProviderCard(
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize(),
                dataModel = item,
            )
        }
    }
}