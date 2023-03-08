package com.example.mh_term_app.data.model.response

import com.example.mh_term_app.data.model.Time

data class ResponsePublicToilet(
    val type: String = "",
    val detailType : String = "",
    val name : String = "",
    val address : String = "",
    val oldAddress : String = "",
    val unisex : String = "",
    val menBow : String = "",
    val menUrine : String = "",
    val menHandicapBow : String = "",
    val menHandicapUrine : String = "",
    val menChildrenBow : String = "",
    val menChildrenUrine : String = "",
    val womenBow : String = "",
    val womenHandicapBow : String = "",
    val womenChildrenBow : String = "",
    val managementName : String = "",
    val phone : String = "",
    val time : Time = Time(),
    val latitude : String = "",
    val longitude : String = "",
    val referenceDate : String = ""
)