package com.example.mh_term_app.data.model.request

data class RequestReview(
    val placeId : String,
    val placeName : String,
    val placeType : String,
    val writer : String,
    val writerType : String,
    val content : String,
    val rating : Double,
    val likeCount : Double,
    val like : MutableList<String>?,
    val date : String
)