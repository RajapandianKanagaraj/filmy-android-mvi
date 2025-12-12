package com.android.filmy.tracking.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsManagerModule {
    @Binds
    abstract fun bindAnalyticsManager(analyticsManager: AnalyticsManagerImpl): AnalyticsManager
}