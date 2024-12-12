package com.example.car_request2.ui.option.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.car_request2.databinding.ItemOptionBinding
import com.example.car_request2.network.model.Option

class OptionViewHolder (val binding: ItemOptionBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Option?, onClick: ((Option) -> Unit)?) {

        binding.apply {
            this.tvDriverName.text = model?.name

            this.tvDescription.text = model?.description

            this.tvVehicle.text = model?.vehicle

            this.tvRating.text = model?.review?.rating.toString()

            this.tvFare.text = model?.value.toString()

            this.btnChooseDriver.setOnClickListener {
                model?.apply {
                    onClick?.invoke(this)
                }
            }

        }

    }

}