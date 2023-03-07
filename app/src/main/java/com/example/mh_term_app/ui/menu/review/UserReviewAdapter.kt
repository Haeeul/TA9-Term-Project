package com.example.mh_term_app.ui.menu.review

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mh_term_app.R
import com.example.mh_term_app.data.model.response.ResponseReviewList
import com.example.mh_term_app.databinding.RvItemUserReviewBinding

class UserReviewAdapter : RecyclerView.Adapter<UserReviewAdapter.UserReviewViewHolder>() {
    var data = mutableListOf<ResponseReviewList>()

    @SuppressLint("NotifyDataSetChanged")
    internal fun setReviewList(data: MutableList<ResponseReviewList>?) {
        if (data != null) this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserReviewViewHolder {
        val binding : RvItemUserReviewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_user_review,
            parent,
            false
        )

        return UserReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserReviewViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class UserReviewViewHolder(val binding : RvItemUserReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : ResponseReviewList){
            binding.item = item

//            if(item.like != null && item.like.contains(MHApplication.prefManager.userNickname)){
//                binding.btnReviewLike.setReviewIcon(true)
//            }else{
//                binding.btnReviewLike.setReviewIcon(false)
//            }
        }
    }
}