package com.example.managerapplication.data.model

data class ChargingStationListResponse(
    val items : MutableList<ChargingStation>
)

data class ChargingStation(
    val fcltyNm : String,
    val ctprvnNm : String,
    val signguNm : String,
    val signguCode : String,
    val rdnmadr : String,
    val lnmadr : String,
    val latitude : String,
    val longitude : String,
    val instlLcDesc : String,
    val weekdayOperOpenHhmm : String,
    val weekdayOperColseHhmm : String,
    val satOperOperOpenHhmm : String,
    val satOperCloseHhmm : String,
    val holidayOperOpenHhmm	 : String,
    val smtmUseCo : String,
    val airInjectorYn : String,
    val moblphonChrstnYn : String,
    val institutionNm : String,
    val institutionPhoneNumber : String,
    val referenceDate : String,
    val instt_code : String
)
