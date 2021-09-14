package com.priambudi19.myfileapp.util

import android.app.Activity
import android.content.Context
import android.net.Uri

fun Uri.getName(context: Context) : String? {
        var name : String? = null
        val cursor = context.contentResolver.query(
            this,
            arrayOf(android.provider.MediaStore.Files.FileColumns.DISPLAY_NAME),
            null,
            null,
            null
        )
        cursor?.let { cr ->
            if (cr.moveToFirst()) {
                val result = Uri.parse(cr.getString(0)).lastPathSegment
                name = result!!
            }
            cr.close()
        }
    return name

}