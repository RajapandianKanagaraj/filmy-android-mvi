package com.android.filmy

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.android.filmy.tracking.core.AnalyticsDispatchApplicationManager
import com.datadog.android.Datadog
import com.datadog.android.DatadogSite
import com.datadog.android.core.configuration.BatchSize
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.core.configuration.UploadFrequency
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.rum.Rum
import com.datadog.android.rum.RumConfiguration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FilmyApp : Application() {
    @Inject lateinit var analyticsDispatchApplicationManager: AnalyticsDispatchApplicationManager

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(analyticsDispatchApplicationManager)
        initDatadog()
    }

    private fun initDatadog() {
        val environment = if (BuildConfig.DEBUG) "dev" else "prod"
        val variant = BuildConfig.BUILD_TYPE

        val configuration = Configuration.Builder(
            clientToken = BuildConfig.DATADOG_CLIENT_TOKEN,
            env = environment,
            variant = variant
        )
            .setUseDeveloperModeWhenDebuggable(true)
            .useSite(DatadogSite.US5)
            .setBatchSize(BatchSize.SMALL)
            .setUploadFrequency(UploadFrequency.FREQUENT)
            .build()

        Datadog.initialize(this, configuration, trackingConsent = TrackingConsent.GRANTED)

        val rumConfiguration = RumConfiguration.Builder(BuildConfig.DATADOG_RUM_APP_ID)
//            .trackUserInteractions()
//            .trackLongTasks(100L)
//            .trackBackgroundEvents(enabled = true)
            .trackNonFatalAnrs(enabled = true)
            .setSessionSampleRate(if (BuildConfig.DEBUG) 100f else 20f)
            .setTelemetrySampleRate(100f)
            .build()

        Rum.enable(rumConfiguration)

        Datadog.setUserInfo(
            id = "raj_0722",
            name = "Raj",
            email = "rajapandian.km@gmail.com",
            extraInfo = mapOf(
                "app_version" to BuildConfig.VERSION_NAME,
                "version_code" to BuildConfig.VERSION_CODE.toString(),
                "build_type" to BuildConfig.BUILD_TYPE,
                "environment" to environment
            )
        )
    }
}