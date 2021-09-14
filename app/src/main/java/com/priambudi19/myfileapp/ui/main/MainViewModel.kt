package com.priambudi19.myfileapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.priambudi19.myfileapp.data.repo.MainRepository

class MainViewModel(private val repo: MainRepository) : ViewModel() {
  fun getListFile() = repo.getListFile().asLiveData()
}
