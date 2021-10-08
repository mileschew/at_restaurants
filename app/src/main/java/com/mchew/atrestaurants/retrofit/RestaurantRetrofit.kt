package com.mchew.atrestaurants.retrofit

import com.mchew.atrestaurants.BuildConfig
import com.mchew.atrestaurants.model.network.RestaurantSearchNetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantRetrofit {

    @GET("textsearch/json")
    suspend fun getRestaurantsFromSearch(
        @Query("query") query: String,
        @Query("type") type: String = "restaurant",
        @Query("key") key: String = BuildConfig.PLACE_API_KEY
    ): RestaurantSearchNetworkResult

    //TODO suspend fun getPhoto()

    //TODO suspend fun getRestaurantDetails()
}
