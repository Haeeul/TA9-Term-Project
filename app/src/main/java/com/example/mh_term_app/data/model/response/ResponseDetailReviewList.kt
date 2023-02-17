package com.example.mh_term_app.data.model.response

data class ResponseDetailReviewList(
    val data : MutableList<DetailReviewItem>
)

data class DetailReviewItem(
    val name : String,
    val type : String,
    val content : String,
    val rating : Float,
    val like : Boolean,
    val likeCount : Int
)
