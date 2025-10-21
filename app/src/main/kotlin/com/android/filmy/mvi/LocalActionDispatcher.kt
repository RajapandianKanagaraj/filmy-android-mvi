package com.android.filmy.mvi

import androidx.compose.runtime.staticCompositionLocalOf

val LocalActionDispatcher = staticCompositionLocalOf<ActionDispatcher> {
    NoOpActionDispatcher()
}