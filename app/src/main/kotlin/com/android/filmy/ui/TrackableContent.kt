package com.android.filmy.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onFirstVisible
import androidx.compose.ui.layout.onVisibilityChanged
import com.android.filmy.analytics.LocalTrackingContext
import com.android.filmy.mvi.AnalyticsAction
import com.filmy.tracking.TrackingContext
import com.filmy.tracking.TrackingParam
import com.filmy.tracking.TrackingSubject
import com.android.filmy.mvi.LocalActionDispatcher
import com.android.filmy.mvi.UiAction
import com.filmy.tracking.AnalyticsEvent
import com.filmy.tracking.Environment
import com.filmy.tracking.EventType
import com.filmy.tracking.SubjectDescription

@Composable
fun TrackableContent(
    trackingParam: TrackingParam,
    modifier: Modifier = Modifier,
    content: @Composable (modifier: Modifier) -> Unit
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

    val subjectDescription = remember(trackingParam.id, trackingParam.name) {
        SubjectDescription(
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
//        DisposableEffect(trackingSubject.id) {
//            actionDispatcher.dispatch(
//                action = AnalyticsAction.OnVisible(
//                    event = AnalyticsEvent(
//                        eventType = EventType.ON_VISIBLE,
//                        subjectDescription = subjectDescription,
//                        environment = Environment(
//                            customerGuid = "",
//                            deviceGuid = "",
//                            os = "Android",
//                            device = "Pixel 10",
//                        )
//                    )
//                )
//            )
//
//            onDispose {
//                actionDispatcher.dispatch(
//                    action = AnalyticsAction.OnInVisible(
//                        event = AnalyticsEvent(
//                            eventType = EventType.ON_INVISIBLE,
//                            subjectDescription = subjectDescription,
//                            environment = Environment(
//                                customerGuid = "",
//                                deviceGuid = "",
//                                os = "Android",
//                                device = "Pixel 10",
//                            )
//                        )
//                    )
//                )
//            }
//        }

        val visibilityModifier = modifier
            .onFirstVisible {
                actionDispatcher.dispatch(
                    action = AnalyticsAction.InViewPort(
                        event = AnalyticsEvent(
                            eventType = EventType.IN_VIEW_PORT,
                            subjectDescription = subjectDescription,
                            environment = Environment(
                                customerGuid = "",
                                deviceGuid = "",
                                os = "Android",
                                device = "Pixel 10",
                            )
                        )
                    )
                )
            }
            .onVisibilityChanged(
                minFractionVisible = 0.5f,
                minDurationMs = 500,
            ) { visible ->
                if (visible) {
                    actionDispatcher.dispatch(
                        action = AnalyticsAction.OnVisible(
                            event = AnalyticsEvent(
                                eventType = EventType.ON_VISIBLE,
                                subjectDescription = subjectDescription,
                                environment = Environment(
                                    customerGuid = "",
                                    deviceGuid = "",
                                    os = "Android",
                                    device = "Pixel 10",
                                )
                            )
                        )
                    )
                } else {
                    actionDispatcher.dispatch(
                        action = AnalyticsAction.OnInVisible(
                            event = AnalyticsEvent(
                                eventType = EventType.ON_INVISIBLE,
                                subjectDescription = subjectDescription,
                                environment = Environment(
                                    customerGuid = "",
                                    deviceGuid = "",
                                    os = "Android",
                                    device = "Pixel 10",
                                )
                            )
                        )
                    )
                }
            }
        content(visibilityModifier)
    }
}