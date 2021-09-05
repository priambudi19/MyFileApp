package com.priambudi19.myfileapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _isGranted = MutableLiveData(false)
    val isGranted: LiveData<Boolean> get() = _isGranted

    fun grantPermission() = _isGranted.postValue(true)
}
