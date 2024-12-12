package com.example.car_request2.network.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ErrorResponse(
    @SerializedName("error_code")
    val errorCode: String,
    @SerializedName("error_description")
    val errorDescription: String,
): Serializable
