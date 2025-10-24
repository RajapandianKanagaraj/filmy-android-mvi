package com.android.filmy.di

import com.android.filmy.core.SegmentRepository
import com.android.filmy.core.SegmentRepositoryImpl
import com.android.filmy.data.FeedRepository
import com.android.filmy.data.FeedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds abstract fun bindSegmentRepository(repo: SegmentRepositoryImpl): SegmentRepository

    @Binds abstract fun bindMovieRepository(repo: FeedRepositoryImpl): FeedRepository
}