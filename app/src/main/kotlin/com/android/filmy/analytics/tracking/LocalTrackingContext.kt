package com.android.filmy.analytics.tracking

import androidx.compose.runtime.staticCompositionLocalOf
val LocalTrackingContext = staticCompositionLocalOf<TrackingContext?> {
    null
}

//val LocalTrackingSubject = staticCompositionLocalOf<List<TrackingSubject>> {
//    emptyList()
//}

//val LocalTrackingAncestorChain = staticCompositionLocalOf<String> {
//    ""
//}