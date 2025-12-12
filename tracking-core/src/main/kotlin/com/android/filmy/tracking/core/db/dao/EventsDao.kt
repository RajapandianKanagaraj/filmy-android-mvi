package com.android.filmy.tracking.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.filmy.tracking.core.db.entities.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(event: Event): Long

    @Query("SELECT * FROM events LIMIT :limit")
    fun getEventsByLimit(limit: Int): List<Event>

    @Query("DELETE FROM events WHERE id IN (:events)")
    fun deleteEvents(events: List<Long>)

    @Query("SELECT COUNT(id) FROM events")
    fun getEventsCount(): Int

    @Query("DELETE FROM events")
    suspend fun clearAll()
}