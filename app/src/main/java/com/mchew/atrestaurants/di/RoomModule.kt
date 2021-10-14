package com.mchew.atrestaurants.di

import android.content.Context
import androidx.room.Room
import com.mchew.atrestaurants.room.RestaurantDao
import com.mchew.atrestaurants.room.RestaurantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideRestaurantDB(
        @ApplicationContext context: Context
    ): RestaurantDatabase {
        return Room.databaseBuilder(
            context,
            RestaurantDatabase::class.java,
            RestaurantDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideRestaurantDAO(restaurantDatabase: RestaurantDatabase): RestaurantDao {
        return restaurantDatabase.restaurauntDao()
    }
}