package com.example.managerapplication.data.repository

import com.example.managerapplication.data.remote.ManagerServiceImpl

class PlaceRepository {
    private val remoteDataSource = ManagerServiceImpl.service

    suspend fun getChargingStation(key : String) = remoteDataSource.getChargingStationList(key)
}