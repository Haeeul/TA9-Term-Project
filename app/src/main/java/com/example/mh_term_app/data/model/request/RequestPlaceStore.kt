package com.example.mh_term_app.data.model.request

import com.example.mh_term_app.data.model.StoreTime
import com.example.mh_term_app.data.model.Time

data class RequestPlaceStore(
    val type: String = "",
    val address: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val name: String = "",
    val phone: String = "",
    val time: StoreTime = StoreTime(
        Time("-2","-2","-2","-2"),
        Time("-2","-2","-2","-2"), Time("-2","-2","-2","-2")
    ),
    val detailType: String = "",
    val targetList: MutableList<String>? = null,
    val warningList: MutableList<String>? = null,
    val plusInfo: String = ""
)