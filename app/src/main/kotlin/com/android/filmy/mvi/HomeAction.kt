package com.android.filmy.mvi

sealed interface HomeAction {
    object LoadNowPlayingMovies: HomeAction
}