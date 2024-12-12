package com.example.car_request2.network.model

import java.io.Serializable

data class Option(
    val id: Int,
    val name: String,
    val description: String,
    val vehicle: String,
    val review: Review,
    val value: Double
): Serializable