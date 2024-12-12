package com.example.car_request2.ui.history

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.car_request2.R
import com.example.car_request2.databinding.ActivityHistoryBinding
import com.example.car_request2.network.model.HistoryResponse
import com.example.car_request2.network.model.Ride
import com.example.car_request2.ui.Dialog
import com.example.car_request2.ui.history.adapter.AdapterHistory
import retrofit2.Response

class HistoryActivity: AppCompatActivity() {

    private lateinit var mBinding: ActivityHistoryBinding

    private var mViewModel: HistoryViewModel? = null

    private var mDriveId : Int? = null

    private var mAdapter: AdapterHistory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[HistoryViewModel::class.java]

        mBinding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        configAdapter()
        setSpinner()
        setListener()
    }

    fun setSpinner(){
        val spinner: Spinner = mBinding.spinnerDrivers
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        //spinner.setSelection(1)
    }

    fun setListener(){
        mBinding.spinnerDrivers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                mDriveId = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                mDriveId = null
            }
        }

        mBinding.btnFilter.setOnClickListener {

            if (mBinding.btnFilter.isEnabled) {
                mBinding.btnFilter.isEnabled = false

                mViewModel?.getHistory(mBinding.etUserId.text.toString(), mDriveId)
            }
        }

        mViewModel?.mResponse?.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable?, i: Int) {
                mBinding.showLoader.isVisible = false
                val resource: Response<HistoryResponse>? = mViewModel?.mResponse?.get()

                if (resource?.isSuccessful==true) {
                    callResponseSuccess(resource)
                } else {
                    callResponseError(resource)
                }
                mBinding.btnFilter.isEnabled = true
            }
        })

    }

    private fun callResponseSuccess(response: Response<HistoryResponse>){
        if (response.body() == null || response.body()?.rides.isNullOrEmpty()){
            Dialog.showRoundedErrorDialog(this,"Indisponivel", "No momento não há historico")
            return
        }
        setListAdapter(response.body()?.rides)
    }

    private fun callResponseError(response: Response<HistoryResponse>?){
        var ErrorResponse = mViewModel?.decodifcError(response)
        Dialog.showRoundedErrorDialog(this, "Erro", ErrorResponse?.errorDescription ?: "-")
    }

    fun configAdapter() {

        mAdapter = AdapterHistory()

        mBinding.recyclerViewRides.apply {
            this.adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    fun setListAdapter(data: List<Ride?>?) {
        mAdapter?.submitList(ArrayList(data))

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}