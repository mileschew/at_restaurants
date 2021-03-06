package com.mchew.atrestaurants.model.domain

import java.io.Serializable as JSerializable
import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: String,
    val name: String,
    val rating: Float?,
    val ratingCount: Int,
    val priceLevel: Int?,
    val address: String,
    val coordinates: Coordinates,
    val isOpenNow: Boolean,
    val photoReference: String?,
    var isFavorite: Boolean = false
) : JSerializable {
    @Serializable
    data class Coordinates(
        val latitude: Double,
        val longitude: Double
    ) : JSerializable
}
