package com.example.managerapplication.data.repository

import com.example.managerapplication.data.remote.ManagerServiceImpl

class PlaceRepository {
    private val remoteDataSource = ManagerServiceImpl.service

    suspend fun getChargingStation(key : String) = remoteDataSource.getChargingStationList(key)
    suspend fun getMovementCenter(key : String) = remoteDataSource.getMovementCenterList(key)
    suspend fun getPublicToiletList(key : String) = remoteDataSource.getPublicToiletList(key)
}