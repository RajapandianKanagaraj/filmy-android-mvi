package com.android.filmy.di

import com.android.filmy.mvi.ActionDispatcher
import com.android.filmy.mvi.ActionDispatcherImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {
    @Binds
    @Singleton
    abstract fun actionDispatcher(dispatcher: ActionDispatcherImpl): ActionDispatcher
}