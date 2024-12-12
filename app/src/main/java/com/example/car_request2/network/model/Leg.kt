package com.example.car_request2.network.model

import java.io.Serializable

data class Leg(
    val distanceMeters: Long,
    val duration: String,
    val staticDuration: String,
    val polyline: Polyline,
    val startLocation: StartLocation,
    val endLocation: EndLocation,
    val steps: List<Step>,
    val localizedValues: LocalizedValues,
): Serializable