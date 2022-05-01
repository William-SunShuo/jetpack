package com.example.jetpack1.bindingUtils

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @BindingAdapter("app:hideIfZero")
    @JvmStatic fun hideIfZero(view: View, num:Int){
        view.visibility = if (num == 0) View.GONE else View.VISIBLE
    }

}