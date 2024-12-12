package com.example.car_request2.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.car_request2.R
import com.example.car_request2.databinding.ItemHistoricoBinding
import com.example.car_request2.databinding.ItemOptionBinding
import com.example.car_request2.network.model.Option
import com.example.car_request2.network.model.Ride

class AdapterHistory:
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListModel : ArrayList<Ride?> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemHistoricoBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_historico,
            parent,
            false
        )

        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HistoryViewHolder -> {
                holder.bind(mListModel.getOrNull(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return mListModel.size
    }

    fun submitList(list: ArrayList<Ride?>) {
        mListModel = list
        notifyDataSetChanged()
    }
}