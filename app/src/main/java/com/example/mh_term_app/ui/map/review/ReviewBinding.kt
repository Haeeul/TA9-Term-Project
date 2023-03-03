package com.example.mh_term_app.ui.map.review

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mh_term_app.data.model.response.ResponseReviewList

object ReviewBinding {
    @JvmStatic
    @BindingAdapter("setReviewList")
    fun setReviewList(recyclerView: RecyclerView, list : MutableList<ResponseReviewList>?){
        if(recyclerView.adapter!=null) with(recyclerView.adapter as DetailReviewAdapter){
            list?.let {
                setReviewList(list)
            }
        }
    }

}