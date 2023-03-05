package com.example.mh_term_app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReportPlaceAddress(
    val type : String,
    val address : String,
    val detailAddress : String,
    val latitude : Double,
    val longitude : Double
) : Parcelable
