package com.capstone.ditalent.utils

sealed class Response<out R> private constructor() {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val uiText: UiText) : Response<Nothing>()
    object Loading : Response<Nothing>()
}