package com.example.mh_term_app.data.model.response

data class ResponseReviewList(
    val reviewId : String,
    val placeId : String,
    val placeName : String,
    val placeType : String,
    val userNickname : String,
    val userType : String,
    val content : String,
    val rating : Float,
    val likeCount : Double,
    val like : MutableList<String>?,
    val date : String
)