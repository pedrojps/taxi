package com.example.car_request2.network.model

import java.io.Serializable

data class Geocoding(
    val geocoderStatus: Map<String, Any>,
    val type: List<String>,
    val placeId: String,
): Serializable
