package com.mchew.atrestaurants.di

import com.mchew.atrestaurants.repository.RestaurantRepository
import com.mchew.atrestaurants.repository.RestaurantRepositoryImpl
import com.mchew.atrestaurants.retrofit.RestaurantRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RestaurantModule {

    @Provides
    fun providesRestaurantRepository(
        networkDataSource: RestaurantRetrofit
    ): RestaurantRepository {
        return RestaurantRepositoryImpl(networkDataSource)
    }
}