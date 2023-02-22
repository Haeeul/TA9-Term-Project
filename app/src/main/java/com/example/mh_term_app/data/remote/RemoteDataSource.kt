package com.example.mh_term_app.data.remote

import com.example.mh_term_app.data.model.response.RequestReportStore

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
    suspend fun postReportStore(store : RequestReportStore) : Boolean
}