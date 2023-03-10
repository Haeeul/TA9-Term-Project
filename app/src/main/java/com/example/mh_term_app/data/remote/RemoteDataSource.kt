package com.example.mh_term_app.data.remote

import com.example.mh_term_app.data.model.request.*
import com.example.mh_term_app.data.model.response.*

interface RemoteDataSource {
    // phone auth
    suspend fun getValidatePhone(phoneNum: String) : Boolean

    // sign up
    suspend fun getValidateId(id : String) : Boolean
    suspend fun getValidateNick(nickname : String) : Boolean
    suspend fun postSignUp(id : String, password : String, nickname : String, type : String) : Boolean

    // sign in
    suspend fun postSignIn(id : String, password : String) : Boolean

    // new report
    suspend fun postReportStore(store : RequestPlaceStore) : Boolean
    suspend fun postReportFacility(facility: RequestPlaceFacility) : Boolean

    // place
    suspend fun getCategoryList(type: String) : MutableList<ResponseCategoryPlace>
    suspend fun getPlaceRating(id : String) : Float
    suspend fun getStoreInfo(id : String) : RequestPlaceStore
    suspend fun getFacilityInfo(id : String) : RequestPlaceFacility
    suspend fun getChargingInfo(id : String) : ResponseChargingStation
    suspend fun getCenterInfo(id : String) : ResponseMoveCenter
    suspend fun getToiletInfo(id : String) : ResponsePublicToilet

    // update place info
    suspend fun postUpdateAddress(place : RequestUpdatePlaceAddress) : Boolean
    suspend fun postUpdateStoreInfo(store : RequestUpdateStoreInfo) : Boolean
    suspend fun postUpdateFacilityInfo(facility : RequestUpdateFacilityInfo) : Boolean

    // review
    suspend fun postReview(review : RequestReview) : Boolean
    suspend fun getReview(placeId : String) : MutableList<ResponseReviewList>
    suspend fun getUserReview(userId : String) : MutableList<ResponseReviewList>

    // update user info
    suspend fun postNewUserInfo(id : String, nickname: String, type: String) : Boolean

    // search
    suspend fun getSearchInfo(name : String) : ResponseCategoryPlace
}