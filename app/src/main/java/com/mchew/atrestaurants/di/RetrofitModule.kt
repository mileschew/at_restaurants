package com.mchew.atrestaurants.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mchew.atrestaurants.retrofit.RestaurantRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit


@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val contentType = MediaType.parse("application/json") ?: throw Throwable("Error creating JSON MediaType")
        val json = Json {
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    fun provideRestaurantService(
        retrofit: Retrofit
    ): RestaurantRetrofit {
        return retrofit.create(RestaurantRetrofit::class.java)
    }
}