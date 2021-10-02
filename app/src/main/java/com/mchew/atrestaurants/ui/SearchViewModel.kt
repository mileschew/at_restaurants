package com.mchew.atrestaurants.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mchew.atrestaurants.core.DataState
import com.mchew.atrestaurants.model.domain.Restaurant
import kotlinx.coroutines.delay

class SearchViewModel : ViewModel() {

    val restaurantList = liveData {
        emit(DataState.Loading(null))
        delay(3000)
        emit(DataState.Success(
            listOf(
                Restaurant("restaurant 1"),
                Restaurant("restaurant 2"),
                Restaurant("restaurant 3"),
                Restaurant("restaurant 4"),
                Restaurant("restaurant 5"),
            )
        ))
    }

}