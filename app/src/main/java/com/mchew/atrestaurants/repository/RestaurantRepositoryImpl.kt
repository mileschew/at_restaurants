package com.mchew.atrestaurants.repository

import com.mchew.atrestaurants.core.DataState
import com.mchew.atrestaurants.model.network.PlaceResultStatus
import com.mchew.atrestaurants.model.network.toDomain
import com.mchew.atrestaurants.retrofit.RestaurantRetrofit
import kotlinx.coroutines.flow.flow

class RestaurantRepositoryImpl(
    private val networkDataSource: RestaurantRetrofit
) : RestaurantRepository {

    override suspend fun getRestaurants(searchQuery: String) = flow {
        emit(DataState.Loading)
        kotlin.runCatching {
            networkDataSource.getRestaurantsFromSearch(searchQuery)
        }.onSuccess {
            when(PlaceResultStatus.valueOf(it.status)) {
                PlaceResultStatus.OK -> {
                    emit(DataState.Success(it.toDomain()))
                }
                else -> {
                    //TODO flesh out error
                    emit(DataState.Error(Throwable("Error")))
                }
            }
        }.onFailure {
            emit(DataState.Error(it))
        }
    }
}