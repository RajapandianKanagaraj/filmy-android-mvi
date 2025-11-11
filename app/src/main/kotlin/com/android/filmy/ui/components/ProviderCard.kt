package com.android.filmy.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.android.filmy.analytics.tracking.LocalTrackingContext
import com.android.filmy.model.ProviderDataModel
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.mvi.ProviderAction
import com.android.filmy.mvi.UiAction.onViewClicked

@Composable
fun ProviderCard(
    modifier: Modifier = Modifier,
    dataModel: ProviderDataModel,
) {
    val actionDispatcher = LocalActionDispatcher.current
    val parentContext = LocalTrackingContext.current

//    Card(
//        modifier = modifier
//            .height(120.dp)
//            .aspectRatio(16f / 9f)
//
//    ) {
//        Box(modifier = Modifier.fillMaxSize()) {
//            AsyncImage(
//                model = posterUrl,
//                contentDescription = provideName,
//                contentScale = ContentScale.Fit,
//                modifier = modifier
//                    .align(Alignment.Center)
//                    .padding(16.dp)
//                    .fillMaxSize()
//            )
//        }
//    }

    Card(
        modifier = modifier
            .wrapContentSize(),
        onClick = {
            actionDispatcher.dispatch(onViewClicked(dataModel.trackingParam, parentContext))
            actionDispatcher.dispatch(ProviderAction.OnProviderSelected(dataModel))
        }
    ) {
        AsyncImage(
            model = dataModel.logoUrl,
            contentDescription = dataModel.providerName,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}