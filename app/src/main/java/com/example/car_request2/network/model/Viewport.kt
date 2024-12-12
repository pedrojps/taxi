package com.example.car_request2.network.model

import java.io.Serializable

data class Viewport(
    val low: LatLng,
    val high: LatLng,
): Serializable
