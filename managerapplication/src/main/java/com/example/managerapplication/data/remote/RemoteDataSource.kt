package com.example.managerapplication.data.remote

import com.example.managerapplication.data.model.request.RequestMovementCenter

interface RemoteDataSource {
    // center
    suspend fun postCenterList(center: RequestMovementCenter) : Boolean
}