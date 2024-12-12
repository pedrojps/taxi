package com.example.car_request2.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EstimateRequest(
    @SerializedName("customer_id")
    val customerId: String,
    val origin: String,
    val destination: String
): Serializable
