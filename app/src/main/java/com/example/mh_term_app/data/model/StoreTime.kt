package com.example.mh_term_app.data.model


data class StoreTime(
    var weekTime : Time,
    var saturdayTime : Time,
    var mondayTime : Time
)

data class Time(
    var openHourTxt : String,
    var openMinuteTxt : String,
    var closeHourTxt : String,
    var closeMinuteTxt : String
)
