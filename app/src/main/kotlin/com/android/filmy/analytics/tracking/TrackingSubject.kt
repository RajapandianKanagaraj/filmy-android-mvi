package com.android.filmy.analytics.tracking

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class TrackingSubject(
    val id: String,
    val name: String,
    val role: String,
    val metadata: Map<String, String>? = null,
    val parentId: String? = null,
    val parentName: String? = null,
    val ancestorChain: String? = null,
    val indexWithInParent: Int = 0,
) {
    companion object {
        val Empty = TrackingSubject(id = "", name = "", role = "", indexWithInParent = 0)
    }
}

private val json = Json {
    encodeDefaults = true
    prettyPrint = true
}
fun TrackingSubject.toJsonString() = json.encodeToString(this)
