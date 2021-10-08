package com.mchew.atrestaurants.repository

import com.mchew.atrestaurants.core.DataState
import com.mchew.atrestaurants.model.domain.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    suspend fun getRestaurants(searchQuery: String): Flow<DataState<List<Restaurant>>>
}