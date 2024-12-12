package com.example.car_request2.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProvider
import com.example.car_request2.databinding.ActivityMainBinding
import com.example.car_request2.network.model.EstimateRequest
import com.example.car_request2.network.model.EstimateResponse
import com.example.car_request2.ui.Dialog
import com.example.car_request2.ui.ScreenManager
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    private var mViewModel: MainViewModel? = null

    private var mEstimateRequest: EstimateRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[MainViewModel::class.java]

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setListener()
    }

    private fun setListener(){

        mBinding.btnEstimateRide.setOnClickListener {

            if (mBinding.btnEstimateRide.isEnabled) {
                mBinding.btnEstimateRide.isEnabled = false
                onClick()
            }
        }

        mBinding.btnHistory.setOnClickListener {
            ScreenManager.toGoHistoryView(this)
        }

        mViewModel?.mResponse?.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable?, i: Int) {
                mBinding.showLoader.isVisible = false
                val resource: Response<EstimateResponse>? = mViewModel?.mResponse?.get()

                if (resource?.isSuccessful==true) {
                    callResponseSuccess(resource)
                } else {
                    callResponseError(resource)
                }
            }
        })
    }

    private fun callResponseSuccess(response: Response<EstimateResponse>){
        if (response.body() == null || response.body()?.options.isNullOrEmpty()){
            Dialog.showRoundedErrorDialog(this,"Indisponivel", "No momento não há motoristas dispoiniveis para esse trajeto")
            return
        }
        ScreenManager.toGoOptionView(this, response.body(), mEstimateRequest)
    }

    private fun callResponseError(response: Response<EstimateResponse>?){
        var ErrorResponse = mViewModel?.decodifcError(response)
        Dialog.showRoundedErrorDialog(this, "Erro", ErrorResponse?.errorDescription ?: "-")
        mBinding.btnEstimateRide.isEnabled = true
    }

    private fun onClick(){
        mBinding.showLoader.isVisible = true

        mEstimateRequest = EstimateRequest(
            mBinding.etUserId.text.toString(),
            mBinding.etOrigin.text.toString(),
            mBinding.etDestination.text.toString()
        )

        mEstimateRequest?.apply {
            mViewModel?.estimate(this)
        }

    }
}