package com.android.filmy.ui

import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.filmy.tracking.TrackingSubject
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.mvi.UiAction


fun Modifier.dispatchOnAppear(trackingSubject: TrackingSubject) = composed {
    val actionDispatcher = LocalActionDispatcher.current
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
    this
}