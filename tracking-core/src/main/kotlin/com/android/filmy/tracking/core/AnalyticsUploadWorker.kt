package com.android.filmy.tracking.core

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.await
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

class AnalyticsUploadWorker {

}

//@HiltWorker
//class AnalyticsUploadWorker @AssistedInject constructor(
//    @Assisted val appContext: Context,
//    @Assisted workerParams: WorkerParameters,
//    val repository: AnalyticsRepository,
//): CoroutineWorker(appContext, workerParams) {
//    override suspend fun doWork(): Result {
//        val eventsToUpload = repository.getEvents(20)
//        if (eventsToUpload.isEmpty()) return Result.Success()
//
//        try {
//            eventsToUpload.forEach { event ->
////                val jsonPayload = Gson().toJson(event)
//                Log.i("TrackingSubject", "event: ${event.name}, description: ${event.subjectDescription.name}")
//            }
//
//            val successfulIds = eventsToUpload.map { it.id }
//            repository.removeEvents(successfulIds)
//
//            return Result.Success()
//        } catch (e: Throwable) {
//            return Result.retry()
//        }
//    }
//}