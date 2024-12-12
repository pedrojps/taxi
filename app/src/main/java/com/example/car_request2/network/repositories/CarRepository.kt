package com.example.car_request2.network.repositories;

import com.example.car_request2.network.model.ConfirmRequest
import com.example.car_request2.network.model.ConfirmResponse
import com.example.car_request2.network.model.EstimateRequest
import com.example.car_request2.network.model.EstimateResponse
import com.example.car_request2.network.model.HistoryResponse
import com.example.car_request2.network.remoteDataSourse.CarRemoteDataSource

import io.reactivex.Single
import retrofit2.Response

class CarRepository private constructor() {

    private val mRepositoriesCarRemoteDataSource: CarRemoteDataSource =
        CarRemoteDataSource.getInstance()

    companion object {
        @Volatile
        private var INSTANCE: CarRepository? = null

        fun getInstance(): CarRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CarRepository().also { INSTANCE = it }
            }
        }
    }

    fun clearInstance() {
        INSTANCE = null
    }

    fun estimate(request: EstimateRequest):
            Single<Response<EstimateResponse>>? {
        return mRepositoriesCarRemoteDataSource.estimate(request)
    }

    fun confirm(request: ConfirmRequest):
            Single<Response<ConfirmResponse>>? {
        return mRepositoriesCarRemoteDataSource.confirm(request)
    }

    fun getHistory(customerId: String, driverId: Int?):
            Single<Response<HistoryResponse>> {
        return mRepositoriesCarRemoteDataSource.getHistory(customerId, driverId)
    }
}


