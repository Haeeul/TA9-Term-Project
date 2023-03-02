package com.example.mh_term_app.data.model.request

data class RequestPlaceFacility(
    val type : String = "",
    val address : String = "",
    val detailAddress : String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val location : String = "",
    val detailType : String = "",
    val targetList : MutableList<String>? = null,
    val warningList : MutableList<String>? = null,
    val plusInfo : String = ""
)