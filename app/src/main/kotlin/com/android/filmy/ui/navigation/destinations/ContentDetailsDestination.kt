package com.android.filmy.ui.navigation.destinations

import com.android.filmy.model.ContentType
import kotlinx.serialization.Serializable

@Serializable
data class ContentDetailsDestination(val contentId: String, val contentType: ContentType)