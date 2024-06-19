package com.emarket.emarketcompose.utils

sealed class Response<T>(val data: T? = null, val message:String? = null) {
    class Success<T>(data: T?=null) : Response<T>(data = data)
    class Error<T>(message: String, data:T? = null) : Response<T>(data = data,message=message)
    class Loading<T> : Response<T>()
}