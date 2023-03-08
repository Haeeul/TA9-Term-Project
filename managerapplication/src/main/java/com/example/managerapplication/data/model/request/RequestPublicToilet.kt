package com.example.managerapplication.data.model.request

import com.example.managerapplication.data.model.CenterTime
import com.example.managerapplication.data.model.Time

data class RequestPublicToilet (
    val type: String,
    val detailType : String,
    val name : String,
    val address : String,
    val oldAddress : String,
    val unisex : String,
    val menBow : String,
    val menUrine : String,
    val menHandicapBow : String,
    val menHandicapUrine : String,
    val menChildrenBow : String,
    val menChildrenUrine : String,
    val womenBow : String,
    val womenHandicapBow : String,
    val womenChildrenBow : String,
    val managementName : String,
    val phone : String,
    val time : Time,
    val latitude : String,
    val longitude : String,
    val referenceDate : String
)