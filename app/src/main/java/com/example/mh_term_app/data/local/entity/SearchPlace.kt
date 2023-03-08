package com.example.mh_term_app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchPlace(
    @PrimaryKey val placeId: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "detailType") val detailType: String?
)