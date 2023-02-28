package com.example.mh_term_app.data.model


data class StoreTime(
    var weekTime : Time = Time("-2","-2","-2","-2"),
    var saturdayTime : Time = Time("-2","-2","-2","-2"),
    var mondayTime : Time = Time("-2","-2","-2","-2")
)

data class Time(
    var openHourTxt : String = "",
    var openMinuteTxt : String = "",
    var closeHourTxt : String = "",
    var closeMinuteTxt : String = ""
)
