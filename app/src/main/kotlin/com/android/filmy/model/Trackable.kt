package com.android.filmy.model

import com.android.filmy.analytics.tracking.TrackingParam

interface Trackable {
    val trackingParam: TrackingParam
}