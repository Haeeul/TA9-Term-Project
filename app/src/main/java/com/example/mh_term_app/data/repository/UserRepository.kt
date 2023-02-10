package com.example.mh_term_app.data.repository

import com.example.mh_term_app.data.remote.RemoteDataSource
import com.example.mh_term_app.data.remote.RemoteDataSourceImpl

class UserRepository() {
    private val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getValidatePhone(phoneNum: String) = remoteDataSource.getValidatePhone(phoneNum)
    suspend fun getValidateNick(nickname : String) = remoteDataSource.getValidateNick(nickname)
    suspend fun postSignUp(phoneNum : String, nickname: String, type : String) = remoteDataSource.postSignUp(phoneNum, nickname, type)
}