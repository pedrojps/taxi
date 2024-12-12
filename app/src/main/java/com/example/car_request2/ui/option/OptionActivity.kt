package com.example.car_request2.ui.option

import android.graphics.Color
import android.icu.text.Transliterator.Position
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.car_request2.R
import com.example.car_request2.databinding.ActivityOptionsBinding
import com.example.car_request2.network.model.ConfirmRequest
import com.example.car_request2.network.model.ConfirmResponse
import com.example.car_request2.network.model.Driver
import com.example.car_request2.network.model.EstimateRequest
import com.example.car_request2.network.model.EstimateResponse
import com.example.car_request2.network.model.Option
import com.example.car_request2.ui.Dialog
import com.example.car_request2.ui.DriveDistance
import com.example.car_request2.ui.ScreenManager
import com.example.car_request2.ui.option.adapter.AdapterOption
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import retrofit2.Response


class OptionActivity  : AppCompatActivity() {

    companion object {
        const val PARAM_OPTION_RESPONSE = "PARAM_OPTION_RESPONSE"
        const val PARAM_OPTION_REQUEST = "PARAM_OPTION_REQUEST"
    }

    private lateinit var mBinding: ActivityOptionsBinding

    private var mViewModel: OptionViewModel? = null

    private var mEstimateResponse: EstimateResponse? = null
    private var mEstimateRequest: EstimateRequest? = null

    var isListClickable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[OptionViewModel::class.java]

        mBinding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        getParam()
        setl()
    }

    private fun setl(){
        mViewModel?.mResponse?.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable?, i: Int) {
                mBinding.showLoader.isVisible = false
                val resource: Response<ConfirmResponse>? = mViewModel?.mResponse?.get()

                if (resource?.isSuccessful==true) {
                    callResponseSuccess(resource)
                } else {
                    callResponseError(resource)
                }
            }
        })
    }

    private fun callResponseSuccess(response: Response<ConfirmResponse>){
        if (response.body() == null || response.body()?.success != true){
            successError()
            return
        }

        ScreenManager.toGoHistoryView(this)
    }

    private fun successError(){
        Dialog.showRoundedErrorDialog(this,getString(R.string.indisponivel_title),getString(R.string.indisponivel_description))
    }

    private fun callResponseError(response: Response<ConfirmResponse>?){
        var ErrorResponse = mViewModel?.decodifcError(response)
        Dialog.showRoundedErrorDialog(this, getString(R.string.error_title), ErrorResponse?.errorDescription ?: "-")
    }

    private fun getParam(){
        mEstimateRequest =
            if (Build.VERSION.SDK_INT >= 33) {
                intent.getSerializableExtra(PARAM_OPTION_REQUEST, EstimateRequest::class.java)
            } else {
                intent.getSerializableExtra(PARAM_OPTION_REQUEST) as? EstimateRequest?
            }

        mEstimateResponse =
            if (Build.VERSION.SDK_INT >= 33) {
                intent.getSerializableExtra(PARAM_OPTION_RESPONSE, EstimateResponse::class.java)
            } else {
                intent.getSerializableExtra(PARAM_OPTION_RESPONSE) as? EstimateResponse?
            }

        setOptionSearch(mEstimateResponse?.options)
        onMapReady()
    }

    private fun setOptionSearch(data: List<Option?>?) {
        if (!data.isNullOrEmpty()){
            val adapter = AdapterOption()

            mBinding.rvDrivers.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(context)
            }

            adapter.submitList(ArrayList(data)) { op ->
                onClick(op)
            }
        }
    }

    fun onMapReady() {
        // Decodifique a polyline
        val encodedPolyline = mEstimateResponse?.routeResponse?.routes?.get(0)?.polyline?.encodedPolyline ?: return
        val decodedPath: List<LatLng> = PolyUtil.decode(encodedPolyline)

        // Construir a URL
        val apiKey = "AIzaSyAJWCDfk_ftFIxuIqtz53BsBmqlblnbaik"
        val staticMapUrl = mViewModel?.buildStaticMapUrlWithPolygon(apiKey, points = decodedPath)

        // Carregar o mapa no ImageView
        Glide.with(this)
            .load(staticMapUrl)
            .into(mBinding.imageView)
    }

    private fun onClick(op: Option){
        val distanceKm = (mEstimateResponse?.distance ?: 0.0)/1000
        if (!DriveDistance.distanceByPermision(op.id, distanceKm)){
            successError()
            return
        }

        if (isListClickable) {
            mBinding.showLoader.isVisible = true
            isListClickable = false

            mViewModel?.confirm(
                ConfirmRequest(
                    mEstimateRequest?.customerId,
                    mEstimateRequest?.origin,
                    mEstimateRequest?.destination,
                    mEstimateResponse?.distance,
                    mEstimateResponse?.duration,
                    Driver(
                        op.id,
                        op.name
                    ),
                    op.value
                )
            )
            mBinding.rvDrivers.postDelayed({ isListClickable = true }, 1000) // Reativa após 1 segundo
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}