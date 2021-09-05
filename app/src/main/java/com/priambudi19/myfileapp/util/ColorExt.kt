package com.priambudi19.myfileapp.util

import android.app.Activity
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Activity.getColorCompat(@ColorRes color: Int): Int {
    return ContextCompat.getColor(applicationContext, color)
}