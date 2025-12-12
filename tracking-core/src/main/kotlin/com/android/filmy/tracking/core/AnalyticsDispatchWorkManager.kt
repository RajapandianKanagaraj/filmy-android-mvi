package com.android.filmy.tracking.core

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.time.Duration.Companion.days
import kotlin.time.toJavaDuration

interface AnalyticsDispatchWorkManager {
    suspend fun scheduleAnalyticsDispatch()
}

class AnalyticsDispatchWorkManagerImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
): AnalyticsDispatchWorkManager {

    override suspend fun scheduleAnalyticsDispatch() {
        val constraints = Constraints(
            requiredNetworkType = NetworkType.CONNECTED,
        )
        val request = PeriodicWorkRequestBuilder<AnalyticsPeriodicWorker>(30, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            ANALYTICS_PERIODIC_DISPATCH_WORKER_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            request,
        ).await()
    }

    private companion object {
        const val ANALYTICS_PERIODIC_DISPATCH_WORKER_TAG = "AnalyticsPeriodicWorker"
    }
}