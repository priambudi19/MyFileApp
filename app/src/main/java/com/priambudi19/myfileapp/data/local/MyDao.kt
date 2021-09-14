package com.priambudi19.myfileapp.data.local

import androidx.room.*
import com.priambudi19.myfileapp.data.model.FileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFile(file: FileEntity) : Long

    @Delete
    suspend fun deleteFile(vararg file: FileEntity) : Int

    @Update()
    suspend fun updateFile(vararg file: FileEntity) : Int

    @Query("SELECT * FROM tb_myfileapp")
    fun getListFile(): Flow<List<FileEntity>>
}