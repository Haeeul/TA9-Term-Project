package com.example.managerapplication.data.model.response

data class BaseResponse<T>(
    val response : Result<T>
)

data class Result<T>(
    val header : ResponseHeader,
    val body : ResponseBody<T>
)

data class ResponseHeader(
    val resultCode : String,
    val resultMsg : String,
    val type : String
)

data class ResponseBody<T>(
    val items : MutableList<T>,
    val totalCount : String,
    val numOfRows : String,
    val pageNo : String
)