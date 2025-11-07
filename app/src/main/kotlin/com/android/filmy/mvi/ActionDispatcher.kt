package com.android.filmy.mvi

import android.util.Log
import androidx.navigation.NavController
import com.android.filmy.analytics.DatadogTracker
import com.android.filmy.analytics.TrackingEvent
import com.android.filmy.analytics.tracking.toJsonString
import com.android.filmy.mvi.UiAction.*
import com.android.filmy.ui.navigation.destinations.ContentDetailsDestination
import javax.inject.Inject

interface ActionDispatcher {
    fun dispatch(action: Action)
}

class ActionDispatcherImpl @Inject constructor(
    private val datadogTracker: DatadogTracker,
): ActionDispatcher {
    private var navController: NavController? = null
    fun initialize(navController: NavController) {
        this.navController = navController
    }

    override fun dispatch(action: Action) {
        when(action) {
            is NavAction -> handleNavAction(action)
            is UiAction -> handleUiAction(action)
        }
    }

    private fun handleNavAction(action: NavAction) {
        when(action) {
            NavAction.navigateBack -> navController?.popBackStack()
            is NavAction.navigateToContentDetails -> {
                navController?.navigate(ContentDetailsDestination(action.contentId, action.contentType))
            }
            is NavAction.navigateToTab -> navController?.navigate(action.tabRoute)
        }
    }

    private fun handleUiAction(action: UiAction) {
        when(action) {
            is ViewAppeared -> datadogTracker.trackViewEvent(TrackingEvent.VIEW_APPEARED, action.attributes)
            is ViewDisappeared -> datadogTracker.trackViewEvent(TrackingEvent.VIEW_DISAPPEARED, action.attributes)
            is ScreenViewed -> datadogTracker.trackPageEvent(TrackingEvent.SCREEN_VIEWED, action.attributes)
            is ViewClicked -> datadogTracker.trackTapEvent(TrackingEvent.VIEW_CLICKED, action.attributes)
            is OnAppeared -> {
                val subject = action.trackingSubject.toJsonString()
                Log.i("TrackingSubject", "OnAppeared: $subject")
            }
            is OnDisappeared -> {
                val subject = action.trackingSubject.toJsonString()
                Log.i("TrackingSubject", "OnDisappeared: $subject")
            }
            is onViewClicked -> {
                val subject = action.trackingSubject.toJsonString()
                Log.i("TrackingSubject", "onViewClicked: $subject")
            }
        }
    }
}

class NoOpActionDispatcher: ActionDispatcher {
    override fun dispatch(action: Action) {
       // No-op
    }
}