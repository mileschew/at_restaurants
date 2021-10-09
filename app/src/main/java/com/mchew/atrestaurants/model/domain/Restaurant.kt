package com.mchew.atrestaurants.model.domain

data class Restaurant(
    val id: String,
    val name: String,
    val rating: Float?,
    val ratingCount: Int,
    val priceLevel: Int?,
    val coordinates: Coordinates,
    var isFavorite: Boolean = false
) {
    data class Coordinates(
        val latitude: Float,
        val longitude: Float
    )
}
