package com.example.managerapplication.data.repository

import com.example.managerapplication.data.model.request.RequestChargingStation
import com.example.managerapplication.data.model.request.RequestMovementCenter
import com.example.managerapplication.data.remote.ManagerServiceImpl
import com.example.managerapplication.data.remote.RemoteDataSource
import com.example.managerapplication.data.remote.RemoteDataSourceImpl

class PlaceRepository {
    private val remoteDataSource = ManagerServiceImpl.service
    private val firebaseRemoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    suspend fun getChargingStation(key : String) = remoteDataSource.getChargingStationList(key)
    suspend fun getMovementCenter(key : String) = remoteDataSource.getMovementCenterList(key)
    suspend fun getPublicToiletList(key : String) = remoteDataSource.getPublicToiletList(key)

    suspend fun postCharging(charging : RequestChargingStation) = firebaseRemoteDataSource.postChargingStation(charging)
    suspend fun postCenter(center : RequestMovementCenter) = firebaseRemoteDataSource.postCenterList(center)
}