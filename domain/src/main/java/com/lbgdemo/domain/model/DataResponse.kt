package com.lbgdemo.domain.model

sealed class DataResponse<out T> {
    class Success<out T>(val data: T) : DataResponse<T>()
    class Error(val msg: String) : DataResponse<Nothing>()
}