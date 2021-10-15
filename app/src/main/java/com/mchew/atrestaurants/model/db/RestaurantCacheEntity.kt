package com.mchew.atrestaurants.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mchew.atrestaurants.model.domain.Restaurant

@Entity(tableName = "restaurants")
data class RestaurantCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "rating")
    val rating: Float?,

    @ColumnInfo(name = "rating_count")
    val ratingCount: Int,

    @ColumnInfo(name = "price_level")
    val priceLevel: Int?,

    @ColumnInfo(name = "is_open_now")
    val isOpenNow: Boolean,

    @ColumnInfo(name = "photo_reference")
    val photoReference: String?,

    @ColumnInfo(name = "address")
    val formattedAddress: String,

    @ColumnInfo(name = "coordinate_lat")
    val coordinateLatitude: Double,

    @ColumnInfo(name = "coordinate_lng")
    val coordinateLongitude: Double,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)

fun RestaurantCacheEntity.toDomain() = Restaurant(
    id = id,
    name = name,
    rating = rating,
    ratingCount = ratingCount,
    priceLevel = priceLevel,
    isOpenNow = isOpenNow,
    photoReference = photoReference,
    formattedAddress = formattedAddress,
    coordinates = Restaurant.Coordinates(coordinateLatitude, coordinateLongitude),
    isFavorite = isFavorite
)

fun Restaurant.toCacheEntity() = RestaurantCacheEntity(
    id = id,
    name = name,
    rating = rating,
    ratingCount = ratingCount,
    priceLevel = priceLevel,
    isOpenNow = isOpenNow,
    photoReference = photoReference,
    formattedAddress = formattedAddress,
    coordinateLatitude = coordinates.latitude,
    coordinateLongitude = coordinates.longitude,
    isFavorite = isFavorite
)