package com.priambudi19.myfileapp.data.repo

import com.priambudi19.myfileapp.data.local.MyDao
import com.priambudi19.myfileapp.data.model.FileEntity
import com.priambudi19.myfileapp.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okio.IOException

class MainRepositoryImpl(private val myDao: MyDao) : MainRepository {
    override suspend fun insertFile(file: FileEntity): Resource<Long> {
        return try {
            val data =  myDao.insertFile(file)
            Resource.Success(data)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }

    }

    override suspend fun deleteFile(vararg file: FileEntity): Resource<Int> {
        return try {
            val data = myDao.deleteFile(*file)
            Resource.Success(data)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }

    override suspend fun updateFile(vararg file: FileEntity): Resource<Int> {
        return try {
            val data =  myDao.updateFile(*file)
            Resource.Success(data)
        } catch (e: IOException) {
            Resource.Error(e.message)
        }
    }

    override fun getListFile(): Flow<List<FileEntity>> = myDao.getListFile()


}