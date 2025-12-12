package com.android.filmy.analytics

import androidx.compose.runtime.staticCompositionLocalOf
import com.filmy.tracking.TrackingContext

val LocalTrackingContext = staticCompositionLocalOf<TrackingContext?> {
    null
}
