package com.example.mh_term_app.data.model.request

data class RequestReportFacility(
    val type : String,
    val address : String,
    val location : String,
    val detailType : String,
    val targetList : MutableList<String>?,
    val warningList : MutableList<String>?,
    val plusInfo : String
)