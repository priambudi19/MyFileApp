package com.priambudi19.myfileapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.priambudi19.myfileapp.data.local.MyDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = MyDatabase.TB_NAME)
data class FileEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "description")
    val description: String = "No Description",

    @ColumnInfo(name = "uri")
    val uri: String = "",

    @ColumnInfo(name = "fileName")
    val fileName: String = "",

    @ColumnInfo(name = "date")
    val date: Long = 0L,

    @ColumnInfo(name = "type")
    val type: Int = 0
) : Parcelable
