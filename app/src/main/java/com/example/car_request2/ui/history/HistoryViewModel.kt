package com.example.car_request2.ui.history

import androidx.databinding.ObservableField
import com.example.car_request2.network.model.HistoryResponse
import com.example.car_request2.ui.base.BaseCarViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class HistoryViewModel: BaseCarViewModel() {

    val mResponse: ObservableField<Response<HistoryResponse>> = ObservableField()

    fun getHistory(customerId: String, driverId: Int?) {
        var id = if (driverId == 0) null else driverId

        val disposable: Disposable? = mParadasRepository.getHistory(customerId, id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(Schedulers.computation())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { listResource ->
                mResponse.set(listResource)
            }

        disposable?.apply {addDisposable(this)}
    }

}
