package com.example.managerapplication.data.model.request

import com.example.managerapplication.data.model.CenterTime

data class RequestMovementCenter(
    val type: String,
    val name : String,
    val address : String,
    val oldAddress : String,
    val latitude : String,
    val longitude : String,
    val carCount : String,
    val carKind : String,
    val slopeCarCount : String,
    val liftCarCount : String,
    val reservationPhone : String,
    val homepage : String,
    val appName : String,
    val reservationTime : CenterTime,
    val carRunTime : CenterTime,
    val aheadTime : String,
    val limit : String,
    val insideArea : String,
    val outsideArea : String,
    val useTarget : String,
    val useCharge : String,
    val managementName : String,
    val phone : String,
    val referenceDate : String
)
