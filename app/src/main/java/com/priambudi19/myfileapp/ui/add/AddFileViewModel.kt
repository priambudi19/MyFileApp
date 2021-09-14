package com.priambudi19.myfileapp.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priambudi19.myfileapp.data.model.FileEntity
import com.priambudi19.myfileapp.data.repo.MainRepository
import com.priambudi19.myfileapp.vo.Resource
import kotlinx.coroutines.launch

class AddFileViewModel(private val repo: MainRepository) : ViewModel() {
    private val _event = MutableLiveData<Event>(Event.Nothing)
    val event: LiveData<Event> get() = _event

    fun insertFile(fileEntity: FileEntity) {
        viewModelScope.launch {
            when (val data = repo.insertFile(fileEntity)) {
                is Resource.Success -> {
                    _event.postValue(Event.Success("File inserted!"))
                }
                is Resource.Error -> {
                    _event.postValue(Event.Error("Failed : ${data.message}"))
                }
            }
        }
    }

    sealed class Event(val message: String? = null) {
        class Success(message: String?) : Event(message)
        class Error(message: String?) : Event(message)
        object Nothing : Event()
    }
}


