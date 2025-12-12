package com.android.filmy.tracking.core

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

interface ApplicationWorker: DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) = Unit
    override fun onStart(owner: LifecycleOwner) = Unit
    override fun onStop(owner: LifecycleOwner) = Unit
}