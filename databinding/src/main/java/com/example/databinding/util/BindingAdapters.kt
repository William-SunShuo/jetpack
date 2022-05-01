package com.example.databinding.util
import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @BindingAdapter("app:hideIfZero")
    @JvmStatic
    fun hideIfZero(view: View, number: Int) {
        view.visibility = if (number == 0) View.GONE else View.VISIBLE
    }


}