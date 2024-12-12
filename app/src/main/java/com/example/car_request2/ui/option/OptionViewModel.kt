package com.example.car_request2.ui.option

import androidx.databinding.ObservableField
import com.example.car_request2.network.model.ConfirmRequest
import com.example.car_request2.network.model.ConfirmResponse
import com.example.car_request2.ui.base.BaseCarViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class OptionViewModel : BaseCarViewModel() {

    val mResponse: ObservableField<Response<ConfirmResponse>> = ObservableField()

    fun confirm(request: ConfirmRequest) {

        val disposable: Disposable? = mParadasRepository.confirm(request)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(Schedulers.computation())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { listResource ->
                mResponse.set(listResource)
            }

        disposable?.apply {addDisposable(this)}
    }

}