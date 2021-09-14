package com.priambudi19.myfileapp.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatToStringDate(): String {
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        .format( Date(this))
}
