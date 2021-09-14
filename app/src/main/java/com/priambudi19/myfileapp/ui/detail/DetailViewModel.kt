package com.priambudi19.myfileapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priambudi19.myfileapp.data.model.FileEntity
import com.priambudi19.myfileapp.data.repo.MainRepository
import com.priambudi19.myfileapp.vo.Resource
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: MainRepository) : ViewModel() {
    private val _event = MutableLiveData<Event>(Event.Nothing)
    val event: LiveData<Event> get() = _event


    fun updateDetail(vararg file : FileEntity) {
        viewModelScope.launch {
          when(repo.updateFile(*file)){
              is Resource.Success -> _event.postValue(Event.EditSuccess("File updated"))
              is Resource.Error -> _event.postValue(Event.EditError("Updating failed"))
          }
        }
    }

    fun deleteFile(vararg file: FileEntity){
        viewModelScope.launch {
            when(repo.deleteFile(*file)){
                is Resource.Success -> _event.postValue(Event.DeleteSuccess("File deleted"))
                is Resource.Error -> _event.postValue(Event.DeleteError("Deleting failed"))
            }
        }
    }

    sealed class Event(val message: String? = null) {
        class EditSuccess(message: String?) : Event(message)
        class EditError(message: String?) : Event(message)
        class DeleteSuccess(message: String?) : Event(message)
        class DeleteError(message: String?) : Event(message)
        object Nothing : Event()
    }
}