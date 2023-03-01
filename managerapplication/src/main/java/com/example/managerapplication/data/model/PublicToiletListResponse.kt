package com.example.managerapplication.data.model

data class PublicToiletListResponse(
    val toiletType : String,
    val toiletNm : String,
    val rdnmadr : String,
    val lnmadr : String,
    val unisexToiletYn : String,
    val menToiletBowlNumber : String,
    val menUrineNumber : String,
    val menHandicapToiletBowlNumber : String,
    val menHandicapUrinalNumber : String,
    val menChildrenToiletBowlNumber : String,
    val menChildrenUrinalNumber : String,
    val ladiesToiletBowlNumber : String,
    val ladiesHandicapToiletBowlNumber : String,
    val ladiesChildrenToiletBowlNumber : String,
    val institutionNm : String,
    val phoneNumber : String,
    val openTime : String,
    val installationYear : String,
    val latitude : String,
    val longitude : String,
//    val toiletPossType : String,
//    val toiletPosiType : String,
//    val careSewerageType : String,
//    val emgBellYn : String,
//    val enterentCctvYn : String,
//    val dipersExchgPosi : String,
//    val modYear : String,
    val referenceDate : String,
    val insttCode : String
)