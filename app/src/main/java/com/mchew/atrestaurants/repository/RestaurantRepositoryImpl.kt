package com.mchew.atrestaurants.repository

import com.mchew.atrestaurants.core.DataState
import com.mchew.atrestaurants.model.domain.Restaurant
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class RestaurantRepositoryImpl : RestaurantRepository {

    override suspend fun getRestaurants() = flow {
        emit(DataState.Loading)
        delay(3000)
        emit(DataState.Success(
            listOf(
                Restaurant("restaurant 1"),
                Restaurant("restaurant 2"),
                Restaurant("restaurant 3"),
                Restaurant("restaurant 4"),
                Restaurant("restaurant 5")
            )
        ))
    }
}