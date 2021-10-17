package com.mchew.atrestaurants.repository

import android.location.Location
import com.mchew.atrestaurants.core.DataState
import com.mchew.atrestaurants.model.domain.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {

    suspend fun getRestaurantsNearby(location: Location): Flow<DataState<List<Restaurant>>>

    suspend fun getRestaurantsFromSearch(searchQuery: String, location: Location?): Flow<DataState<List<Restaurant>>>

    suspend fun updateFavoriteStatus(restaurant: Restaurant, isFavorite: Boolean)
}