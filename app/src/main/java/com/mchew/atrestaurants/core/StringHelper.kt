package com.mchew.atrestaurants.core

import com.mchew.atrestaurants.BuildConfig
import java.lang.StringBuilder

fun Int?.toPriceString(): String {
    return this?.let {
        val sb = StringBuilder()
        for (i in 0 until this) {
            sb.append("$")
        }
        sb.toString()
    }.orEmpty()
}

fun Int.toRaitingCountString() = "($this)"

fun getGooglePlacePhotoUrl(photoReference: String, maxWidth: Int = 300): String {
    return "https://maps.googleapis.com/maps/api/place/photo" +
            "?maxwidth=$maxWidth" +
            "&photo_reference=$photoReference" +
            "&key=${BuildConfig.PLACE_API_KEY}"
}