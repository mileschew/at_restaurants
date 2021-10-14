package com.mchew.atrestaurants.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mchew.atrestaurants.model.db.RestaurantCacheEntity

@Database(entities = [RestaurantCacheEntity::class], version = 2)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun restaurauntDao(): RestaurantDao

    companion object {
        const val DATABASE_NAME = "restaurant_db"
    }
}