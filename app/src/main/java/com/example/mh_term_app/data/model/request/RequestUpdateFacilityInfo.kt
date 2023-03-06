package com.example.mh_term_app.data.model.request

data class RequestUpdateFacilityInfo(
    val id : String = "",
    val type : String = "상세정보",
    val location: String = "",
    val detailType: String = "",
    val targetList: MutableList<String>? = null,
    val warningList: MutableList<String>? = null,
    val plusInfo: String = ""
)