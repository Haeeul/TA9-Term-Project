package com.example.managerapplication.data.remote

import com.example.managerapplication.data.model.request.RequestChargingStation
import com.example.managerapplication.data.model.request.RequestMovementCenter

interface RemoteDataSource {
    // charging
    suspend fun postChargingStation(charging : RequestChargingStation) : Boolean

    // center
    suspend fun postCenterList(center: RequestMovementCenter) : Boolean
}