package com.example.jetpack1
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MyLifecycleObserver : LifecycleObserver {
    
    companion object {
         const val TAG: String = "MyLifecycleObserver"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onEventCreate() {
        Log.i(TAG, "event create")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEventStart() {
        Log.i(TAG, "event start")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onEventResume() {
        Log.i(TAG, "event resume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onEventPause() {
        Log.i(TAG, "event pause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEventStop() {
        Log.i(TAG, "event stop")
    }
    
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onEventDestroy(){
        Log.i(TAG, "event destroy")
    }

}