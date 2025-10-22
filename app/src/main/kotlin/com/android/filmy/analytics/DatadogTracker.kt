package com.android.filmy.analytics

import com.datadog.android.rum.GlobalRumMonitor
import com.datadog.android.rum.RumActionType
import javax.inject.Inject

class DatadogTracker @Inject constructor(): AnalyticsTracker {
    private val rumMonitor = GlobalRumMonitor.get()

    override fun trackViewEvent(
        event: TrackingEvent,
        properties: Map<String, Any>
    ) {
//        rumMonitor.addAction(
//            type = RumActionType.CUSTOM,
//            name = event.name,
//            attributes = properties,
//        )
    }

    override fun trackTapEvent(
        event: TrackingEvent,
        properties: Map<String, Any>
    ) {
//        rumMonitor.addAction(
//            type = RumActionType.TAP,
//            name = event.name,
//            attributes = properties,
//        )
    }

    override fun trackPageEvent(
        event: TrackingEvent,
        properties: Map<String, Any>
    ) {
        rumMonitor.addAction(
            type = RumActionType.CUSTOM,
            name = event.name,
            attributes = properties,
        )
    }
}