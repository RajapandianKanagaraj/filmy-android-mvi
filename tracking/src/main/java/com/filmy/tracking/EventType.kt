package com.filmy.tracking

import kotlinx.serialization.Serializable

@Serializable
enum class EventType(val eventName: String) {
    IN_VIEW_PORT("inViewPort"),
    ON_VISIBLE("onVisible"),
    ON_INVISIBLE("onInvisible"),
    ON_CLICKED("onClicked"),
    ON_NAVIGATE("onNavigate")
}