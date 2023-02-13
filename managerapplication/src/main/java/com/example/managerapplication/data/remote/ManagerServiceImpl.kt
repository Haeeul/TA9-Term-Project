package com.example.managerapplication.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ManagerServiceImpl {
    private const val BASE_URL = "http://api.data.go.kr/openapi/"

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service : ManagerService = retrofit.create(ManagerService::class.java)
}