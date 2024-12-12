package com.example.car_request2.ui.history.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.car_request2.databinding.ItemHistoricoBinding
import com.example.car_request2.databinding.ItemOptionBinding
import com.example.car_request2.network.model.Option
import com.example.car_request2.network.model.Ride

class HistoryViewHolder (val binding: ItemHistoricoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Ride?) {

        binding.apply {
            tvDateTime.text = "${model?.date}"// às ${model.time}"
            tvOrigin.text = "De: ${model?.origin}"
            tvDestination.text = "Para: ${model?.destination}"
            tvDistanceDuration.text = "Distância: ${model?.distance} km, Tempo: ${model?.duration}"
            tvValue.text = "Valor: R$ ${model?.value}"

        }

    }

}