package com.example.car_request2.ui.base

import androidx.lifecycle.ViewModel
import com.example.car_request2.network.model.ErrorResponse
import com.example.car_request2.network.model.EstimateResponse
import com.example.car_request2.network.repositories.CarRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.Response

abstract class BaseCarViewModel : ViewModel() {

    protected val mParadasRepository: CarRepository = CarRepository.getInstance()

    protected val mCompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    override fun onCleared() {
        mCompositeDisposable.clear()
    }

    fun <T> decodifcError(response: Response<T>?):ErrorResponse?{
        val gson = Gson()
        val type = object : TypeToken<ErrorResponse>() {}.type
        var errorResponse: ErrorResponse? = gson.fromJson(response?.errorBody()!!.charStream(), type)
        return errorResponse
    }


}