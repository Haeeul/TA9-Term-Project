package com.example.mh_term_app.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseCategoryPlace(
    val id : String = "",
    val data : PlaceInfo
) : Parcelable

@Parcelize
data class PlaceInfo(
    val type: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val name: String = "",
    val phone: String = "",
    val detailType : String = "",
    val location : String = ""
) : Parcelable