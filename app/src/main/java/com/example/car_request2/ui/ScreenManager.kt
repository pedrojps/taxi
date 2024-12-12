package com.example.car_request2.ui

import android.content.Context
import android.content.Intent
import com.example.car_request2.network.model.EstimateRequest
import com.example.car_request2.network.model.EstimateResponse
import com.example.car_request2.ui.history.HistoryActivity
import com.example.car_request2.ui.option.OptionActivity

object ScreenManager {

    fun toGoOptionView(context:Context?, response: EstimateResponse?, request: EstimateRequest?){
        val intent = Intent(context, OptionActivity::class.java)
        intent.putExtra(OptionActivity.PARAM_OPTION_RESPONSE, response)
        intent.putExtra(OptionActivity.PARAM_OPTION_REQUEST, request)
        context?.startActivity(intent)
    }

    fun toGoHistoryView(context:Context?){
        val intent = Intent(context, HistoryActivity::class.java)
        context?.startActivity(intent)
    }


}
