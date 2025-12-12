package com.android.filmy.tracking.core

import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject

class AnalyticsDispatchApplicationManager @Inject constructor(
    private val analyticsManager: AnalyticsManager,
): ApplicationWorker {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        analyticsManager.scheduleAnalyticsDispatch()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }
}