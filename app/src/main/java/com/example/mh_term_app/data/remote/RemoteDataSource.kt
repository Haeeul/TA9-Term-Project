package com.example.mh_term_app.data.remote

interface RemoteDataSource {
    // sign up
    suspend fun getValidateNick(nickname : String) : Boolean
    suspend fun postSignUp(phoneNum : String, nickname : String, type : String) : Boolean
}