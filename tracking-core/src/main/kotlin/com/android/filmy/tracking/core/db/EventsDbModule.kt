package com.android.filmy.tracking.core.db

import android.content.Context
import com.android.filmy.tracking.core.db.dao.EventsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventsDbModule {

    @Singleton
    @Provides
    fun providesEventsDatabase(@ApplicationContext context: Context): EventsDatabase =
        EventsDatabase.Companion.getInstance(context)

    @Provides
    fun providesEventsDao(db: EventsDatabase): EventsDao = db.eventsDao()
}