package com.mchew.atrestaurants.repository

import android.location.Location
import com.mchew.atrestaurants.core.DataState
import com.mchew.atrestaurants.model.db.toCacheEntity
import com.mchew.atrestaurants.model.db.toDomain
import com.mchew.atrestaurants.model.domain.Restaurant
import com.mchew.atrestaurants.model.network.PlaceResultStatus
import com.mchew.atrestaurants.model.network.toDomain
import com.mchew.atrestaurants.retrofit.RestaurantRetrofit
import com.mchew.atrestaurants.room.RestaurantDao
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class RestaurantRepositoryImpl(
    private val networkDataSource: RestaurantRetrofit,
    private val cacheDataSource: RestaurantDao
) : RestaurantRepository {

    override suspend fun getRestaurantsNearby(location: Location) = flow {
        emit(DataState.Loading)
        kotlin.runCatching {
            networkDataSource.getRestaurantsNearby(
                location = "${location.latitude},${location.longitude}"
            )
        }.onSuccess { networkResult ->
            when (PlaceResultStatus.valueOf(networkResult.status)) {
                PlaceResultStatus.OK -> {
                    // map results to domain object list
                    val domainResults = networkResult.toDomain()
                    domainResults.forEach { result ->
                        // if cached version exists, update the favorite status
                        cacheDataSource.getRestaurant(result.id)?.toDomain()?.let {
                            result.isFavorite = it.isFavorite
                        }
                    }

                    // insert all results into cache
                    val cacheEntities = domainResults.map { it.toCacheEntity() }
                    cacheDataSource.insertRestaurants(*cacheEntities.toTypedArray())

                    // return network list with updated favorite status
                    emit(DataState.Success(domainResults))
                }
                else -> {
                    val error = RuntimeException("Endpoint returned status ${networkResult.status}")
                    Timber.e(error)
                    emit(DataState.Error(error))
                }
            }
        }.onFailure {
            Timber.e(it, "Error retrieving nearby restaurants.")
            emit(DataState.Error(it))
        }
    }

    override suspend fun getRestaurantsFromSearch(searchQuery: String) = flow {
        emit(DataState.Loading)
        kotlin.runCatching {
            networkDataSource.getRestaurantsFromSearch(searchQuery)
        }.onSuccess { networkResult ->
            when (PlaceResultStatus.valueOf(networkResult.status)) {
                PlaceResultStatus.OK -> {
                    // map results to domain object list
                    val domainResults = networkResult.toDomain()
                    domainResults.forEach { result ->
                        // if cached version exists, update the favorite status
                        cacheDataSource.getRestaurant(result.id)?.toDomain()?.let {
                            result.isFavorite = it.isFavorite
                        }
                    }

                    // insert all results into cache
                    val cacheEntities = domainResults.map { it.toCacheEntity() }
                    cacheDataSource.insertRestaurants(*cacheEntities.toTypedArray())

                    // return network list with updated favorite status
                    emit(DataState.Success(domainResults))
                }
                else -> {
                    val error = RuntimeException("Endpoint returned status ${networkResult.status}")
                    Timber.e(error)
                    emit(DataState.Error(error))
                }
            }
        }.onFailure {
            Timber.e(it, "Error retrieving restaurants from search query: $searchQuery.")
            emit(DataState.Error(it))
        }
    }

    override suspend fun updateFavoriteStatus(restaurant: Restaurant, isFavorite: Boolean) {
        Timber.d("Updating favorite status of ${restaurant.id} to $isFavorite")
        restaurant.isFavorite = isFavorite
        val cachedEntity = restaurant.toCacheEntity()
        cacheDataSource.updateRestaurant(cachedEntity)
    }
}