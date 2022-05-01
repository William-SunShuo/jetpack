package com.example.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable


class LikeClass : BaseObservable() {

    var like = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.like)
        }
        @Bindable
        get

}