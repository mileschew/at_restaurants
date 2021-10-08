package com.mchew.atrestaurants.model.network

enum class PlaceResultStatus {
    OK,
    ZERO_RESULTS,
    INVALID_REQUEST,
    OVER_QUERY_LIMIT,
    REQUEST_DENIED,
    UNKNOWN_ERROR
}