package com.example.car_request2.ui.option.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.car_request2.databinding.ItemOptionBinding
import com.example.car_request2.network.model.Option

class OptionViewHolder (val binding: ItemOptionBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Option?, onClick: ((Option) -> Unit)?) {

        binding.apply {
            tvDriverName.text = model?.name
            tvDriverDescription.text = model?.description
            tvVehicle.text = "Veículo: ${model?.vehicle}"
            tvRating.text = "${model?.review?.rating} ★"
            tvReviews.text = "(${model?.review?.comment})"
            tvValue.text = "R$ ${model?.value}"

            this.btnChooseDriver.setOnClickListener {
                model?.apply {
                    onClick?.invoke(this)
                }
            }

        }

    }

}