package com.android.filmy.mvi

import com.android.filmy.analytics.tracking.TrackingContext
import com.android.filmy.analytics.tracking.TrackingParam
import com.android.filmy.analytics.tracking.TrackingSubject
import com.android.filmy.model.ContentType
import com.android.filmy.model.ProviderDataModel

sealed interface Action

sealed interface NavAction: Action {
    data object navigateBack: NavAction
    data class navigateToTab(val tabRoute: Any): NavAction
    data class navigateToContentDetails(val contentId: String, val contentType: ContentType): NavAction
}

sealed interface UiAction: Action {
    data class ViewAppeared(val viewName: String, val attributes: Map<String, Any>): UiAction
    data class ViewDisappeared(val viewName: String, val attributes: Map<String, Any>): UiAction
    data class ScreenViewed(val screenName: String, val attributes: Map<String, Any>): UiAction
    data class ViewClicked(val viewName: String, val attributes: Map<String, Any>): UiAction

    // Raw UI Event Actions
    data class OnAppeared(val trackingSubject: TrackingSubject): UiAction
    data class OnDisappeared(val trackingSubject: TrackingSubject): UiAction
    data class onViewClicked(val trackingModel: TrackingParam, val parentContext: TrackingContext?): UiAction
}

sealed interface ProviderAction: Action {
    data class OnProviderSelected(val provider: ProviderDataModel): ProviderAction
}

