package com.android.filmy.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.android.filmy.analytics.LocalTrackingContext
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