package com.example.car_request2.network.model

import java.io.Serializable

data class GeocodingResults(
    val origin: Geocoding,
    val destination: Geocoding,
): Serializable
