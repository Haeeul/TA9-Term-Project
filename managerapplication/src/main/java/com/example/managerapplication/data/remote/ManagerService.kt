package com.example.managerapplication.data.remote

import com.example.managerapplication.data.model.ChargingStationListResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ManagerService {
    @GET("tn_pubr_public_tfcwker_mvmn_cnter_api")
    suspend fun getChargingStationList(
        @Header("serviceKey") serviceKey : String,
        @Query("pageNo") pageNo : Int,
        @Query("numOfRows") numOfRows : Int,
        @Query("type") type : String,
    ): ChargingStationListResponse
}