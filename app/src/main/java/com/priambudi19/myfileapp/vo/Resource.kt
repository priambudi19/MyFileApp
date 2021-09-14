package com.priambudi19.myfileapp.vo

sealed class Resource<T>(var data: T? = null, val message: String? = "") {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(message: String?) : Resource<T>(message = message)
}