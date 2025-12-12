package com.filmy.tracking

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class SubjectDescription(
    val id: String,
    val name: String,
    val role: String,
    val metadata: Map<String, String>? = null,
    val parentId: String? = null,
    val parentName: String? = null,
    val ancestorChain: String? = null,
    val indexWithInParent: Int = 0,
)

private val json = Json {
    encodeDefaults = true
    prettyPrint = true
}
fun SubjectDescription.toJsonString() = json.encodeToString(this)
