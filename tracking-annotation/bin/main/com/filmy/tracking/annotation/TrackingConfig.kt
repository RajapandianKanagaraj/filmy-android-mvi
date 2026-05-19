package com.filmy.core.tracking.annotations

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
annotation class TrackingConfig(
    val autoTrack: Boolean = false,
)