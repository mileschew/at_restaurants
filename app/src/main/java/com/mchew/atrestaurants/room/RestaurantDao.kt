package com.mchew.atrestaurants.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mchew.atrestaurants.model.db.RestaurantCacheEntity
import com.mchew.atrestaurants.model.domain.Restaurant

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurants(vararg restaurantCacheEntity: RestaurantCacheEntity)

    @Query("SELECT * FROM restaurants")
    suspend fun getAllRestaurants(): List<RestaurantCacheEntity>

    @Query("SELECT * FROM restaurants WHERE id = :id")
    suspend fun getRestaurant(id: String): RestaurantCacheEntity?

    @Update
    suspend fun updateRestaurant(vararg restaurants: RestaurantCacheEntity)
}