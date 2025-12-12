package com.android.filmy.tracking.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsDispatchWorkerModule {

    @Binds
    abstract fun bindAnalyticsDispatchWorkManager(dispatchWorkManager: AnalyticsDispatchWorkManagerImpl): AnalyticsDispatchWorkManager
}