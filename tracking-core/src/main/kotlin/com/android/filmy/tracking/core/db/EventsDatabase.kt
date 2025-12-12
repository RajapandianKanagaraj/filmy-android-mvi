package com.android.filmy.tracking.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.filmy.tracking.core.db.dao.EventsDao
import com.android.filmy.tracking.core.db.entities.Event
import com.android.filmy.tracking.core.db.typeconverters.DescriptionConverter
import com.android.filmy.tracking.core.db.typeconverters.EnvironmentTypeConverter
import kotlin.concurrent.Volatile

@Database(
    entities = [Event::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(DescriptionConverter::class, EnvironmentTypeConverter::class)
abstract class EventsDatabase: RoomDatabase() {

    abstract fun eventsDao(): EventsDao

    companion object {
        @Volatile private var INSTANCE: EventsDatabase? = null

        fun getInstance(context: Context): EventsDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    EventsDatabase::class.java,
                    "analytics_events.db",
                    )
                    .build()
                    .also { INSTANCE = it }
            }
    }
}