package com.example.car_request2.ui.option.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.car_request2.R
import com.example.car_request2.databinding.ItemOptionBinding
import com.example.car_request2.network.model.Option

class AdapterOption:
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListModel : ArrayList<Option?> = arrayListOf()

    private var mOnClick : ((Option) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemOptionBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_option,
            parent,
            false
        )

        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OptionViewHolder -> {
                holder.bind(mListModel.getOrNull(position), mOnClick)
            }
        }
    }

    override fun getItemCount(): Int {
        return mListModel.size
    }

    fun submitList(list: ArrayList<Option?>, onClick: ((Option) -> Unit)?) {
        mListModel = list
        mOnClick = onClick
        notifyDataSetChanged()
    }
}