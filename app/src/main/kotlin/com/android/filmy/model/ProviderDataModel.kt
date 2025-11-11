package com.android.filmy.model

import com.android.filmy.analytics.tracking.TrackingParam

data class ProviderDataModel(
    override val id: String,
    override val contentType: ContentType = ContentType.PROVIDER,
    override val trackingParam: TrackingParam = TrackingParam.Empty,
    val displayPriority: Int,
    val logoPath: String,
    val logoUrl: String,
    val providerName: String,
): SegmentDataModel, Trackable
