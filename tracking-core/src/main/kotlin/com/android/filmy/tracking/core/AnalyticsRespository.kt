package com.android.filmy.tracking.core

import android.util.Log
import com.android.filmy.tracking.core.db.dao.EventsDao
import com.android.filmy.tracking.core.db.entities.Event
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface AnalyticsRepository {
    suspend fun addEvent(event: Event)
    suspend fun getEvents(limit: Int): List<Event>
    suspend fun removeEvents(events: List<Long>)
    suspend fun getEventsCount(): Int
    suspend fun clearAllEvents()
}

class AnalyticsRepositoryImpl @Inject constructor(
    private val eventsDao: EventsDao
): AnalyticsRepository {
    override suspend fun addEvent(event: Event) {
        Log.i("TrackingSubject", "event: ${event.name}, description: ${event.subjectDescription.name}")
        eventsDao.insert(event)
    }

    override suspend fun getEvents(limit: Int): List<Event> {
        return eventsDao.getEventsByLimit(limit)
    }

    override suspend fun getEventsCount(): Int {
        return eventsDao.getEventsCount()
    }

    override suspend fun removeEvents(events: List<Long>) {
        eventsDao.deleteEvents(events)
    }

    override suspend fun clearAllEvents() {
        eventsDao.clearAll()
    }
}
