package com.example.car_request2.network.model

import java.io.Serializable

data class Route(
    val polyline: Polyline,
    val viewport: Viewport
):Serializable
