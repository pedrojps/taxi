package com.example.car_request2.network.model

import java.io.Serializable

data class LocalizedValues(
    val distance: String,
    val duration: String? = null,
    val staticDuration: String,
): Serializable
