package com.mchew.atrestaurants.core

sealed class DataState<out R> {

    data class Success<T>(val data: T) : DataState<T>()
    object Loading : DataState<Nothing>()
    data class Error(val throwable: Throwable) : DataState<Nothing>()

    fun isLoading() = this is Loading
}