package com.example.mh_term_app.data.remote

interface RemoteDataSource {
    //sign
    suspend fun getValidatePhone(phoneNum: String) : Boolean

    // sign up
    suspend fun getValidateNick(nickname : String) : Boolean
    suspend fun postSignUp(id : String, password : String, nickname : String, type : String) : Boolean

    // new login
    suspend fun getValidateId(id : String) : Boolean
}