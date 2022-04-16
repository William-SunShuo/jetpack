package com.example.jetpack1
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class MyLifecycleObserver1 : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.i(TAG, "event onStateChanged: ${event.name}")
    }

    companion object {
        const val TAG: String = "MyLifecycleObserver"
    }

}