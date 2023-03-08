package com.example.mh_term_app.data.model.response

import com.example.mh_term_app.data.model.Time

data class ResponseChargingStation (
    val type : String = "",
    val name : String = "",
    val address : String = "",
    val oldAddress : String = "",
    val latitude : String = "",
    val longitude : String = "",
    val description : String = "",
    val time : ChargingTime = ChargingTime(),
    val sameUse : String = "",
    val airUse : String = "",
    val phoneUse : String = "",
    val managementName : String = "",
    val phone : String = "",
    val referenceDate : String = ""
)

data class ChargingTime(
    var weekTime : Time = Time("-2","-2","-2","-2"),
    var saturdayTime : Time = Time("-2","-2","-2","-2"),
    var mondayTime : Time = Time("-2","-2","-2","-2")
)
