package com.android.filmy.tracking.core

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import javax.inject.Inject

class AnalyticsPeriodicWorker @Inject constructor(
    appContext: Context,
    workerParams: WorkerParameters,
    private val analyticsRepository: AnalyticsRepository
): CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val eventsToUpload = analyticsRepository.getEvents(20)
        if (eventsToUpload.isEmpty()) return Result.Success()

        try {
            eventsToUpload.forEach { event ->
                Log.i("TrackingSubject", "event: ${event.name}, description: ${event.subjectDescription.name}")
            }

            val successfulIds = eventsToUpload.map { it.id }
            analyticsRepository.removeEvents(successfulIds)
            return Result.Success()
        } catch (e: Throwable) {
            return Result.retry()
        }
    }
}