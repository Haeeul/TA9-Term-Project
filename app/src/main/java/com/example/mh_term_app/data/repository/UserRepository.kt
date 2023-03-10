package com.example.mh_term_app.data.repository

import com.example.mh_term_app.data.remote.RemoteDataSource
import com.example.mh_term_app.data.remote.RemoteDataSourceImpl

class UserRepository() {
    private val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getValidatePhone(phoneNum: String) = remoteDataSource.getValidatePhone(phoneNum)

    suspend fun getValidateId(id : String) = remoteDataSource.getValidateId(id)
    suspend fun getValidateNick(nickname : String) = remoteDataSource.getValidateNick(nickname)
    suspend fun postSignUp(id : String, password : String, nickname: String, type : String) = remoteDataSource.postSignUp(id, password, nickname, type)
    suspend fun postSignIn(id : String, password: String) = remoteDataSource.postSignIn(id, password)

    suspend fun postNewUserInfo(id : String, nickname: String, type : String) = remoteDataSource.postNewUserInfo(id, nickname, type)
}