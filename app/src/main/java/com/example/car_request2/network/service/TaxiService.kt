package com.example.car_request2.network.service

import com.example.car_request2.network.model.*
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface TaxiService {
    @POST("ride/estimate")
    fun estimate(@Body request: EstimateRequest): Single<Response<EstimateResponse>>

    @PATCH("ride/confirm")
    fun confirm(@Body request: ConfirmRequest): Single<Response<ConfirmResponse>>

    @GET("ride/{customer_id}")
    fun getHistory(
        @Path("customer_id") customerId: String,
        @Query("driver_id") driverId: Int?
    ): Single<Response<HistoryResponse>>
}