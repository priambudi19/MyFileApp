package com.priambudi19.myfileapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.priambudi19.myfileapp.data.model.FileEntity

@Database(entities = [FileEntity::class],version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun dao() : MyDao
    companion object{
        const val TB_NAME = "tb_myfileapp"
        const val DB_NAME = "myfileapp.db"
    }
}