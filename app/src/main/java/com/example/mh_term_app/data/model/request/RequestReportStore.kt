package com.example.mh_term_app.data.model.request

import com.example.mh_term_app.data.model.StoreTime

data class RequestReportStore(
    val type: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val phone: String,
    val time: StoreTime,
    val detailType: String,
    val targetList: MutableList<String>?,
    val warningList: MutableList<String>?,
    val plusInfo: String
)