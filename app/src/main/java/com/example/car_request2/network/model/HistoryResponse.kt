package com.example.car_request2.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HistoryResponse(
    @SerializedName("customer_id")
    val customerId: String,
    val rides: List<Ride>
): Serializable
