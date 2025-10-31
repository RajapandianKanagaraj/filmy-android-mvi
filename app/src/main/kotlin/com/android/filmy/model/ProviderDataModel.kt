package com.android.filmy.model

data class ProviderDataModel(
    override val id: String,
    override val contentType: ContentType = ContentType.PROVIDER,
    val displayPriority: Int,
    val logoPath: String,
    val logoUrl: String,
    val providerName: String,
): SegmentDataModel
