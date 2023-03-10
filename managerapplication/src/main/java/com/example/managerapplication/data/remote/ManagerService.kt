package com.example.managerapplication.data.remote

import com.example.managerapplication.data.model.response.BaseResponse
import com.example.managerapplication.data.model.response.ChargingStationListResponse
import com.example.managerapplication.data.model.response.MovementCenterListResponse
import com.example.managerapplication.data.model.response.PublicToiletListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ManagerService {
    @GET("tn_pubr_public_electr_whlchairhgh_spdchrgr_api")
    suspend fun getChargingStationList(
        @Query("serviceKey") serviceKey : String,
        @Query("pageNo") pageNo : Int = 1,
        @Query("numOfRows") numOfRows : Int = 1,
        @Query("type") type : String = "json",
        @Query("signguNm") ctprvnNm : String = "영등포구"
    ): BaseResponse<ChargingStationListResponse>

    @GET("tn_pubr_public_tfcwker_mvmn_cnter_api")
    suspend fun getMovementCenterList(
        @Query("serviceKey") serviceKey : String,
        @Query("pageNo") pageNo : Int = 1,
        @Query("numOfRows") numOfRows : Int = 1,
        @Query("type") type : String = "json"
    ): BaseResponse<MovementCenterListResponse>

    @GET("tn_pubr_public_toilet_api")
    suspend fun getPublicToiletList(
        @Query("serviceKey") serviceKey : String,
        @Query("pageNo") pageNo : Int = 1,
        @Query("numOfRows") numOfRows : Int = 15,
        @Query("type") type : String = "json",
        @Query("institutionNm") institutionNm : String = "서울특별시 영등포구청"
    ): BaseResponse<PublicToiletListResponse>
}