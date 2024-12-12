package com.example.car_request2.network.model

import java.io.Serializable

data class EstimateResponse(
    val origin: Coordinates,
    val destination: Coordinates,
    val distance: Double,
    val duration: String,
    val options: List<Option>,
    val routeResponse: RouteResponse
):Serializable