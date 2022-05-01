package com.example.databinding
import androidx.lifecycle.MutableLiveData

class LiveDataLike {
    var like = MutableLiveData(0)

    fun onLike() {
        like.value = (like.value ?: 0) + 1
    }
}