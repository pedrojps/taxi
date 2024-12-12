package com.example.car_request2.network.remoteDataSourse

import com.example.car_request2.network.model.ConfirmRequest
import com.example.car_request2.network.model.ConfirmResponse
import com.example.car_request2.network.model.EstimateRequest
import com.example.car_request2.network.model.EstimateResponse
import com.example.car_request2.network.model.HistoryResponse
import com.example.car_request2.network.service.TaxiService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import retrofit2.http.Query


class CarRemoteDataSource: BaseRemoteDataSourse() {

    companion object {
        @Volatile
        private var INSTANCE: CarRemoteDataSource? = null

        fun getInstance(): CarRemoteDataSource {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CarRemoteDataSource().also { INSTANCE = it }
            }
        }
    }

    fun clearInstance() {
        INSTANCE = null
    }

    fun estimate(request: EstimateRequest): Single<Response<EstimateResponse>>? {
        val mainService: TaxiService = getMainService(TaxiService::class.java)

        return mainService.estimate(request)
            .observeOn(Schedulers.computation())
    }

    fun confirm(request: ConfirmRequest): Single<Response<ConfirmResponse>>? {
        val mainService: TaxiService = getMainService(TaxiService::class.java)

        return mainService.confirm(request)
            .observeOn(Schedulers.computation())
    }

    fun getHistory(customerId: String, driverId: Int?): Single<Response<HistoryResponse>> {
        val mainService: TaxiService = getMainService(TaxiService::class.java)

        return mainService.getHistory(customerId, driverId)
            .observeOn(Schedulers.computation())
    }
}