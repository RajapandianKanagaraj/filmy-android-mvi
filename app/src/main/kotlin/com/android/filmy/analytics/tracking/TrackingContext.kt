package com.android.filmy.analytics.tracking

import java.util.concurrent.atomic.AtomicInteger

data class TrackingContext(
    val id: String,
    val name: String,
    val childCount: AtomicInteger = AtomicInteger(0),
    val ancestorChain: String? = null,
)
