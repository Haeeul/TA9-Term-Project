package com.example.mh_term_app.ui.map.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mh_term_app.R
import com.example.mh_term_app.data.model.response.DetailReviewItem
import com.example.mh_term_app.databinding.RvItemDetailReviewBinding

class DetailReviewAdapter : RecyclerView.Adapter<DetailReviewAdapter.DetailReviewViewHolder>() {
    var data = mutableListOf<DetailReviewItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailReviewViewHolder {
        val binding : RvItemDetailReviewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_detail_review,
            parent,
            false
        )

        return DetailReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailReviewViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class DetailReviewViewHolder(val binding : RvItemDetailReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : DetailReviewItem){
            binding.item = item
        }
    }
}