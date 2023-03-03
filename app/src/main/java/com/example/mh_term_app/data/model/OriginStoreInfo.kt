package com.example.mh_term_app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OriginStoreInfo(
    val name: String = "",
    val phone: String = "",
    val time: MutableList<MutableList<String>>? = null,
    val detailType: String = "",
    val targetList: MutableList<String>? = null,
    val warningList: MutableList<String>? = null,
    val plusInfo: String = ""
) : Parcelable