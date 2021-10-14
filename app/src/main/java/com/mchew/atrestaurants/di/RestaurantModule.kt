package com.mchew.atrestaurants.di

import com.mchew.atrestaurants.repository.RestaurantRepository
import com.mchew.atrestaurants.repository.RestaurantRepositoryImpl
import com.mchew.atrestaurants.retrofit.RestaurantRetrofit
import com.mchew.atrestaurants.room.RestaurantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RestaurantModule {

    @Provides
    fun provideRestaurantRepository(
        networkDataSource: RestaurantRetrofit,
        cacheDataSource: RestaurantDao
    ): RestaurantRepository {
        return RestaurantRepositoryImpl(networkDataSource, cacheDataSource)
    }
}