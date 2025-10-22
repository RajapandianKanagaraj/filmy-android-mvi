package com.android.filmy.core

sealed interface ContentSegment

sealed interface MovieContentSegment : ContentSegment {
    object NowPlayingMovies : MovieContentSegment
    object UpcomingMovies : MovieContentSegment
    object PopularMovies : MovieContentSegment
    object TrendingMovies : MovieContentSegment
}

sealed interface TvContentSegment : ContentSegment {
    object NowPlayingSeries : TvContentSegment
    object PopularSeries : TvContentSegment
    object TrendingSeries : TvContentSegment
    object UpcomingSeries : TvContentSegment
}