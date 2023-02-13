package com.example.managerapplication.data.remote

import com.example.managerapplication.data.model.ChargingStationListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ManagerService {
    @GET("tn_pubr_public_electr_whlchairhgh_spdchrgr_api")
    suspend fun getChargingStationList(
        @Query("serviceKey") serviceKey : String,
        @Query("pageNo") pageNo : Int = 0,
        @Query("numOfRows") numOfRows : Int = 10,
        @Query("type") type : String = "json"
    ): ChargingStationListResponse
}