package com.example.mh_term_app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdatePlaceAddress(
    val id : String,
    val type : String,
    val address : String,
    val detailAddress : String,
    val latitude : Double,
    val longitude : Double,
    val newAddress : String,
    val newDetailAddress : String,
    val newLatitude : Double,
    val newLongitude : Double
) : Parcelable
