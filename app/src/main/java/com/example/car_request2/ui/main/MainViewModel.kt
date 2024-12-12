package com.example.car_request2.ui.main

import androidx.databinding.ObservableField
import com.example.car_request2.network.model.EstimateRequest
import com.example.car_request2.network.model.EstimateResponse
import com.example.car_request2.ui.base.BaseCarViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainViewModel : BaseCarViewModel() {

    val mResponse: ObservableField<Response<EstimateResponse>> = ObservableField()

    fun estimate(request: EstimateRequest) {

        val disposable: Disposable? = mParadasRepository.estimate(request)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(Schedulers.computation())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { listResource ->
                mResponse.set(listResource)
            }

        disposable?.apply {addDisposable(this)}
    }

}