package com.mchew.atrestaurants.core

sealed class DataState<out T> {

    data class Success<T>(val data: T) : DataState<T>()
    data class Loading<T>(val data: T?) : DataState<T>()
    data class Error<T>(val throwable: Throwable) : DataState<T>()

    fun isLoading() = this is Loading
}