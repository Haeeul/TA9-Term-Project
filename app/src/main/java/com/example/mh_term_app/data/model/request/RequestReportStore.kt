package com.example.mh_term_app.data.model.request

data class RequestReportStore(
    val type : String,
    val address : String,
    val name : String,
    val phone : String,
    val time : String,
    val detailType : String,
    val targetList : MutableList<String>?,
    val warningList : MutableList<String>?,
    val plusInfo : String
)