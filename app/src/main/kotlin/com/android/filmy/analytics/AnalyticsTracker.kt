package com.android.filmy.analytics

interface AnalyticsTracker {
    fun trackViewEvent(event: TrackingEvent, properties: Map<String, Any>)
    fun trackTapEvent(event: TrackingEvent, properties: Map<String, Any>)
    fun trackPageEvent(event: TrackingEvent, properties: Map<String, Any>)
}