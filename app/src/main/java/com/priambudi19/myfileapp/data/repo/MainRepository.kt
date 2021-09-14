package com.priambudi19.myfileapp.data.repo

import com.priambudi19.myfileapp.data.model.FileEntity
import com.priambudi19.myfileapp.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun insertFile(file: FileEntity)  : Resource<Long>
    suspend fun deleteFile(vararg file: FileEntity) : Resource<Int>
    suspend fun updateFile(vararg file: FileEntity) : Resource<Int>
    fun getListFile(): Flow<List<FileEntity>>
}