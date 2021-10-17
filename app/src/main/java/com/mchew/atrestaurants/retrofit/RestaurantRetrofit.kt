package com.mchew.atrestaurants.retrofit

import com.mchew.atrestaurants.BuildConfig
import com.mchew.atrestaurants.model.network.RestaurantNearbyNetworkResult
import com.mchew.atrestaurants.model.network.RestaurantSearchNetworkResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantRetrofit {

    // Get restaurants around user
    @GET("nearbysearch/json")
    suspend fun getRestaurantsNearby(
        @Query("location") location: String,
        @Query("radius") radius: Int = 50_000,
        @Query("type") type: String = "restaurant",
        @Query("key") key: String = BuildConfig.PLACE_API_KEY
    ): RestaurantNearbyNetworkResult

    // Search for restaurants with no location
    @GET("textsearch/json")
    suspend fun getRestaurantsFromSearch(
        @Query("query") query: String,
        @Query("type") type: String = "restaurant",
        @Query("key") key: String = BuildConfig.PLACE_API_KEY
    ): RestaurantSearchNetworkResult

    // Search for restaurants around user
    @GET("textsearch/json")
    suspend fun getRestaurantsFromSearch(
        @Query("query") query: String,
        @Query("location") location: String,
        @Query("type") type: String = "restaurant",
        @Query("key") key: String = BuildConfig.PLACE_API_KEY
    ): RestaurantSearchNetworkResult

}
