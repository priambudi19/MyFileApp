package com.priambudi19.myfileapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.priambudi19.myfileapp.data.local.MyDatabase

@Entity(tableName = MyDatabase.TB_NAME)
data class FileEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "uri")
    val uri: String = "",
    @ColumnInfo(name = "date")
    val date: Long = 0L,
)
