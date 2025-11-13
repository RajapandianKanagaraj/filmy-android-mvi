package com.filmy.tracking

data class TrackingParam(
    val id: String,
    val name: String,
    val role: String,
    val metadata: Map<String, String> = emptyMap(),
) {
    companion object {
        val Empty = TrackingParam(id = "", name = "", role = "")
    }
}
