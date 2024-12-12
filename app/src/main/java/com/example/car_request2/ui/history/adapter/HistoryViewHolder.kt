package com.example.car_request2.ui.history.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.car_request2.R
import com.example.car_request2.databinding.ItemHistoricoBinding
import com.example.car_request2.network.model.Ride
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Date


class HistoryViewHolder (val binding: ItemHistoricoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Ride?) {

        binding.apply {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            try {
                val date: Date? = dateFormat.parse(model?.date)
                tvDateTime.text = itemView.context.getString(R.string.format_datetime,date)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            tvOrigin.text =  itemView.context.getString(R.string.de,model?.origin)
            tvDestination.text = itemView.context.getString(R.string.para,model?.destination)

            tvDistanceDuration.text = itemView.context.getString(R.string.distance_by_time,model?.distance,model?.duration)
            tvValue.text = itemView.context.getString(R.string.value,model?.value)

        }

    }

}