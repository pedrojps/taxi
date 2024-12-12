package com.example.car_request2.network.model

import java.io.Serializable

data class NavigationInstruction(
    val maneuver: String,
    val instructions: String,
): Serializable