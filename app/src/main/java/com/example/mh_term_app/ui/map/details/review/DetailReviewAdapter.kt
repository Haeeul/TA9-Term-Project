package com.example.mh_term_app.ui.map.details.review

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.data.model.response.ResponseReviewList
import com.example.mh_term_app.databinding.RvItemDetailReviewBinding
import com.example.mh_term_app.utils.databinding.BindingAdapter.setReviewIcon

class DetailReviewAdapter : RecyclerView.Adapter<DetailReviewAdapter.DetailReviewViewHolder>() {
    var data = mutableListOf<ResponseReviewList>()

    @SuppressLint("NotifyDataSetChanged")
    internal fun setReviewList(data: MutableList<ResponseReviewList>?) {
        if (data != null) this.data = data
        notifyDataSetChanged()
    }

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
        fun bind(item : ResponseReviewList){
            binding.item = item

            if(item.like != null && item.like.contains(MHApplication.prefManager.userNickname)){
                binding.btnReviewLike.setReviewIcon(true)
            }else{
                binding.btnReviewLike.setReviewIcon(false)
            }
        }


    }
}