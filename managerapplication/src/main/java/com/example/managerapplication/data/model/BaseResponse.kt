package com.example.managerapplication.data.model

data class BaseResponse<T>(
    val response : Result<T>
)

data class Result<T>(
    val header : ResponseHeader,
    val body : T
)

data class ResponseHeader(
    val resultCode : String,
    val resultMsg : String,
    val type : String
)