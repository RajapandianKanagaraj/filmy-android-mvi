package com.android.filmy.di

import com.android.filmy.data.MovieRepository
import com.android.filmy.data.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds abstract fun bindMovieRepository(repo: MovieRepositoryImpl): MovieRepository
}