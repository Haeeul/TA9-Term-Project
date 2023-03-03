package com.example.mh_term_app.data.model

data class UpdateStoreInfo(
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