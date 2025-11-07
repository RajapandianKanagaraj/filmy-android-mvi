package com.android.filmy.analytics.tracking

data class TrackingParam(
    val id: String,
    val name: String,
    val role: String,
    val metadata: Map<String, String>? = null,
) {
    companion object {
        val Empty = TrackingParam(id = "", name = "", role = "")
    }
}
