package com.android.filmy.mvi

import android.util.Log
import androidx.navigation.NavController
import com.android.filmy.analytics.DatadogTracker
import com.android.filmy.analytics.TrackingEvent
import com.android.filmy.analytics.tracking.TrackingSubject
import com.android.filmy.analytics.tracking.toJsonString
import com.android.filmy.mvi.UiAction.*
import com.android.filmy.ui.navigation.destinations.ContentDetailsDestination
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

interface ActionDispatcher {
    fun dispatch(action: Action)
    val actions: SharedFlow<Action>
    fun setNavController(navController: NavController)
}

@Singleton
class ActionDispatcherImpl @Inject constructor(
    private val datadogTracker: DatadogTracker,
): ActionDispatcher {
    private var navController: NavController? = null

    private val _actions = MutableSharedFlow<Action>(
        replay = 1,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    override val actions: SharedFlow<Action> = _actions.asSharedFlow()

    override fun setNavController(navController: NavController) {
        this.navController = navController
    }

    override fun dispatch(action: Action) {
        _actions.tryEmit(action)
        when(action) {
            is NavAction -> handleNavAction(action)
            is UiAction -> handleUiAction(action)
            is ProviderAction.OnProviderSelected -> {
//                dispatch(onViewClicked(action.provider.trackingParam))
            }
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
                val trackingParam = action.trackingModel
//                val parentContext = action.parentContext
//                val index = parentContext?.childCount?.getAndIncrement() ?: 0
                val trackingSubject = TrackingSubject(
                    id = trackingParam.id,
                    name = trackingParam.name,
                    role = trackingParam.role,
                    metadata = trackingParam.metadata,
//                    parentId = parentContext?.id,
//                    parentName = parentContext?.name,
//                    indexWithInParent = index,
//                    ancestorChain = parentContext?.ancestorChain + ":" + trackingParam.id + "[$index]"
                )
                Log.i("TrackingSubject", "onViewClicked: ${trackingSubject.toJsonString()}")
            }
        }
    }
}

class NoOpActionDispatcher: ActionDispatcher {
    override fun dispatch(action: Action) {
       // No-op
    }

    override val actions: SharedFlow<Action>
        get() = MutableSharedFlow()

    override fun setNavController(navController: NavController) = Unit
}