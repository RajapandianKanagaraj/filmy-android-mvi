package com.android.filmy.tracking.core.db.entities

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
