package com.example.managerapplication.data.model

class MovementCenterListResponse(
    val items : MutableList<MovementCenter>
)

data class MovementCenter(
    val tfcwkerMvmnCnterNm : String,
    val rdnmadr : String,
    val lnmadr : String,
    val latitude : String,
    val longitude : String,
    val carHoldCo : String,
    val carHoldKnd : String,
    val slopeVhcleCo : String,
    val liftVhcleCo : String,
    val rceptPhoneNumber : String,
    val rceptItnadr : String,
    val appSvcNm : String,
    val weekdayRceptOpenHhmm : String,
    val weekdayRceptColseHhmm : String,
    val wkendRceptOpenHhmm : String,
    val wkendRceptCloseHhmm : String,
    val weekdayOperOpenHhmm : String,
    val weekdayOperColseHhmm : String,
    val wkendOperOpenHhmm : String,
    val wkendOperCloseHhmm : String,
    val beffatResvePd : String,
    val useLmtt : String,
    val insideOpratArea : String,
    val outsideOpratArea : String,
    val useTrget : String,
    val useCharge : String,
    val institutionNm : String,
    val phoneNumber : String,
    val referenceDate : String,
    val insttCode : String,
)