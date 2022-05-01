package com.example.jetpack1.bindingUtils

import android.view.View
import androidx.databinding.BindingConversion

object BindingConverters{

    @BindingConversion
    @JvmStatic fun booleanToVisibility(isNotVisible: Boolean): Int {
        return if (isNotVisible) View.GONE else View.VISIBLE
    }
}