package com.android.filmy.tracking.core

import android.content.Context
import com.android.filmy.tracking.core.db.entities.Event
import com.android.filmy.tracking.core.db.entities.Environment
import com.android.filmy.tracking.core.db.entities.SubjectDescription
import com.filmy.tracking.AnalyticsEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

interface AnalyticsManager {
    fun queueEvent(event: AnalyticsEvent)
    fun scheduleAnalyticsDispatch()
}

class AnalyticsManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: AnalyticsRepositoryImpl,
    private val analyticsDispatchWorkManager: AnalyticsDispatchWorkManager,
) : AnalyticsManager {

    private val BATCH_SIZE = 20
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun queueEvent(event: AnalyticsEvent) {
        val eventEntity = Event(
            name = event.eventType.eventName,
            subjectDescription = SubjectDescription(
                id = event.subjectDescription.id,
                name = event.subjectDescription.name,
                role = event.subjectDescription.role,
                parentId = event.subjectDescription.parentId,
                parentName = event.subjectDescription.parentName,
                ancestorChain = event.subjectDescription.ancestorChain,
                indexWithInParent = event.subjectDescription.indexWithInParent,
                metadata = event.subjectDescription.metadata,
            ),
            environment = Environment(
                customerGuid = event.environment.customerGuid,
                deviceGuid = event.environment.deviceGuid,
                os = event.environment.os,
                device = event.environment.device,
            ),
        )
        scope.launch {
            repository.addEvent(eventEntity)
        }
    }

    override fun scheduleAnalyticsDispatch() {
        scope.launch {
            analyticsDispatchWorkManager.scheduleAnalyticsDispatch()
        }
    }
}