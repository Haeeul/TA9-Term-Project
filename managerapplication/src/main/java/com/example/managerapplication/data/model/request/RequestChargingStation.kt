package com.example.managerapplication.data.model.request

import com.example.managerapplication.data.model.ChargingTime

data class RequestChargingStation (
    val type : String,
    val name : String,
    val address : String,
    val oldAddress : String,
    val latitude : String,
    val longitude : String,
    val description : String,
    val time : ChargingTime,
    val sameUse : String,
    val airUse : String,
    val phoneUser : String,
    val managementName : String,
    val phone : String,
    val referenceDate : String
)