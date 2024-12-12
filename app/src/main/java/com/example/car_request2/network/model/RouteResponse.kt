package com.example.car_request2.network.model

import java.io.Serializable

data class RouteResponse(
    val routes: ArrayList<Route> = ArrayList(),
    val geocodingResults: GeocodingResults
): Serializable
