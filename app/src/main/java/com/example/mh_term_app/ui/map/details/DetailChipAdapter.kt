package com.example.mh_term_app.ui.map.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mh_term_app.R
import com.example.mh_term_app.databinding.RvItemDetailChipBinding
import com.example.mh_term_app.utils.databinding.BindingAdapter.setUserTypeChip

class DetailChipAdapter : RecyclerView.Adapter<DetailChipAdapter.DetailChipViewHolder>() {
    var data = mutableListOf<String>()

    fun replaceAll(array: ArrayList<String>?) {
        array?.let {
            data.run {
                clear()
                addAll(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailChipAdapter.DetailChipViewHolder {
        val binding : RvItemDetailChipBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_detail_chip,
            parent,
            false
        )

        return DetailChipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailChipAdapter.DetailChipViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class DetailChipViewHolder(val binding : RvItemDetailChipBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : String){
            binding.item = item

            binding.rvDetailChip.setUserTypeChip(item)
        }
    }
}