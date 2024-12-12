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
import com.example.car_request2.databinding.ActivityOptionsBinding
import com.example.car_request2.network.model.ConfirmRequest
import com.example.car_request2.network.model.ConfirmResponse
import com.example.car_request2.network.model.Driver
import com.example.car_request2.network.model.EstimateRequest
import com.example.car_request2.network.model.EstimateResponse
import com.example.car_request2.network.model.Option
import com.example.car_request2.ui.Dialog
import com.example.car_request2.ui.option.adapter.AdapterOption
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import retrofit2.Response


class OptionActivity  : AppCompatActivity() , OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

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

        mapView = mBinding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

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
                    callResponseError(resource!!)
                }
            }
        })
    }

    private fun callResponseSuccess(response: Response<ConfirmResponse>){
        if (response.body() == null || response.body()?.success == true){
            Dialog.showRoundedErrorDialog(this,"Indisponivel", "No momento esta indispoiniveis para esse trajeto")
            return
        }

        //ScreenManager.toGoOptionView(this, response.body(), mEstimateRequest)
    }

    private fun callResponseError(response: Response<ConfirmResponse>?){
        var ErrorResponse = mViewModel?.decodifcError(response)
        Dialog.showRoundedErrorDialog(this, "Erro", ErrorResponse?.errorDescription ?: "-")
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

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Decodifique a polyline
        val encodedPolyline = mEstimateResponse?.routeResponse?.routes?.get(0)?.polyline?.encodedPolyline ?: return
        val decodedPath: List<LatLng> = PolyUtil.decode(encodedPolyline)

        // Adicione a polyline no mapa
        googleMap.addPolyline(
            PolylineOptions()
                .addAll(decodedPath)
                .width(10f)
                .color(Color.BLUE)
        )

        // Centralize o mapa
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(decodedPath[0], 12f))
    }

    private fun onClick(op: Option){
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

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}