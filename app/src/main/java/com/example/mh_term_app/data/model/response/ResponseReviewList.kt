package com.example.mh_term_app.data.model.response

data class ResponseReviewList(
    val reviewId : String,
    val placeId : String,
    val placeName : String,
    val placeType : String,
    val writer : String,
    val writerType : String,
    val content : String,
    val rating : Double,
    val likeCount : Double,
    val like : MutableList<String>?
)