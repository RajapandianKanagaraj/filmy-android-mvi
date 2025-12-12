package com.filmy.tracking

import kotlinx.serialization.Serializable

@Serializable
data class AnalyticsEvent(
    val eventType: EventType,
    val subjectDescription: SubjectDescription,
    val environment: Environment,
)
