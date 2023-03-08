package com.example.managerapplication.data.model

data class ChargingTime(
    var weekTime : Time = Time("-2","-2","-2","-2"),
    var saturdayTime : Time = Time("-2","-2","-2","-2"),
    var mondayTime : Time = Time("-2","-2","-2","-2")
)

data class CenterTime(
    var weekTime : Time = Time("-2","-2","-2","-2"),
    var holidayTime : Time = Time("-2","-2","-2","-2"),
)

data class Time(
    var openHourTxt : String = "",
    var openMinuteTxt : String = "",
    var closeHourTxt : String = "",
    var closeMinuteTxt : String = ""
)
