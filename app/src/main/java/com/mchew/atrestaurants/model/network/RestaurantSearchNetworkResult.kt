package com.mchew.atrestaurants.model.network

import com.mchew.atrestaurants.model.domain.Restaurant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantSearchNetworkResult(
    @SerialName("results") val restaurantResults: List<RestaurantResult>,
    val status: String
) {
    @Serializable
    data class RestaurantResult(
        @SerialName("place_id") val id: String,
        val name: String,
        val rating: Float? = null,
        @SerialName("user_ratings_total") val userRatingsTotal: Int,
        @SerialName("price_level") val priceLevel: Int? = null,
        @SerialName("formatted_address") val formattedAddress: String,
        @SerialName("opening_hours") val openingHours: PlaceOpeningHours? = null,
        val geometry: Geometry,
        val photos: List<Photo>

    ) {
        @Serializable
        data class Geometry(
            val location: LatLngLiteral
        ) {
            @Serializable
            data class LatLngLiteral(
                val lat: Float,
                val lng: Float
            )
        }

        @Serializable
        data class Photo(
            val height: Int,
            val width: Int,
            @SerialName("photo_reference") val reference: String
        )

        @Serializable
        data class PlaceOpeningHours(
            @SerialName("open_now") val openNow: Boolean
        )
    }
}

fun RestaurantSearchNetworkResult.toDomain(): List<Restaurant> {
    return restaurantResults.map {
        Restaurant(
            id = it.id,
            name = it.name,
            rating = it.rating,
            ratingCount = it.userRatingsTotal,
            priceLevel = it.priceLevel,
            isOpenNow = it.openingHours?.openNow ?: true,
            photoReference = it.photos.firstOrNull()?.reference,
            formattedAddress = it.formattedAddress,
            coordinates = it.geometry.location.run { Restaurant.Coordinates(lat, lng) }
        )
    }
}