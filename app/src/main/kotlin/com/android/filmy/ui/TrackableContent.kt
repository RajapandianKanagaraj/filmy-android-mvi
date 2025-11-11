package com.android.filmy.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.android.filmy.analytics.tracking.LocalTrackingContext
import com.android.filmy.analytics.tracking.TrackingContext
import com.android.filmy.analytics.tracking.TrackingParam
import com.android.filmy.analytics.tracking.TrackingSubject
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.mvi.UiAction

@Composable
fun TrackableContent(
    trackingParam: TrackingParam,
    content: @Composable () -> Unit
) {
    val actionDispatcher = LocalActionDispatcher.current
    val parentTrackingContext = LocalTrackingContext.current

    val index = remember(parentTrackingContext?.id) {
        parentTrackingContext?.childCount?.getAndIncrement() ?: 0
    }

    val trackingSubject = remember(trackingParam.id, trackingParam.name) {
        TrackingSubject(
            id = trackingParam.id,
            name = trackingParam.name,
            role = trackingParam.role,
            metadata = trackingParam.metadata,
            parentId = parentTrackingContext?.id.orEmpty(),
            parentName = parentTrackingContext?.name.orEmpty(),
            indexWithInParent = index,
            ancestorChain = parentTrackingContext?.ancestorChain.orEmpty() + ":" + trackingParam.id + "[$index]",
        )
    }

    val newTrackingContext = remember(trackingSubject.id, trackingSubject.name) {
        TrackingContext(
            id = trackingSubject.id,
            name = trackingSubject.name,
            ancestorChain = trackingSubject.id, // To get just immediate parent in the Ancestry Chain
//            ancestorChain = trackingSubject.ancestorChain, // To get full ancestry chain
        )
    }
    CompositionLocalProvider(LocalTrackingContext provides newTrackingContext) {
        DisposableEffect(trackingSubject.id) {
            // Dispatch the appear event
            actionDispatcher.dispatch(
                UiAction.OnAppeared(
                    trackingSubject
                )
            )

            // Dispatch disappear event when composable leaves composition
            onDispose {
                actionDispatcher.dispatch(
                    UiAction.OnDisappeared(
                        trackingSubject
                    )
                )
            }
        }
        content()
    }
}