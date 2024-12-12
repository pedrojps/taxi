package com.example.car_request2.network.model

import java.io.Serializable

data class Step(
    val distanceMeters: Long,
    val staticDuration: String,
    val polyline: Polyline,
    val startLocation: StartLocation,
    val endLocation: EndLocation,
    val navigationInstruction: NavigationInstruction,
    val localizedValues: LocalizedValues,
    val travelMode: String,
): Serializable
