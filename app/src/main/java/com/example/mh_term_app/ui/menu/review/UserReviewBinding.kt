package com.example.mh_term_app.ui.menu.review

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mh_term_app.data.model.response.ResponseReviewList

object UserReviewBinding {
    @JvmStatic
    @BindingAdapter("setUserReviewList")
    fun setUserReviewList(recyclerView: RecyclerView, list : MutableList<ResponseReviewList>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as UserReviewAdapter){
            list?.let {
                setReviewList(list)
            }
        }
    }

}