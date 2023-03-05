package com.example.mh_term_app.data.repository

import com.example.mh_term_app.data.model.request.RequestPlaceFacility
import com.example.mh_term_app.data.model.request.RequestPlaceStore
import com.example.mh_term_app.data.model.request.RequestUpdatePlaceAddress
import com.example.mh_term_app.data.model.request.RequestUpdateStoreInfo
import com.example.mh_term_app.data.remote.RemoteDataSource
import com.example.mh_term_app.data.remote.RemoteDataSourceImpl

class MapRepository {
    private val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    suspend fun postReportStore(store: RequestPlaceStore) = remoteDataSource.postReportStore(store)
    suspend fun postReportFacility(facility: RequestPlaceFacility) = remoteDataSource.postReportFacility(facility)

    suspend fun getCategoryList(type : String) = remoteDataSource.getCategoryList(type)
    suspend fun getPlaceRating(id : String) = remoteDataSource.getPlaceRating(id)
    suspend fun getStoreInfo(id : String) = remoteDataSource.getStoreInfo(id)
    suspend fun getFacilityInfo(id : String) = remoteDataSource.getFacilityInfo(id)

    suspend fun postUpdateAddress(place : RequestUpdatePlaceAddress) = remoteDataSource.postUpdateAddress(place)
    suspend fun postUpdateStoreInfo(store : RequestUpdateStoreInfo) = remoteDataSource.postUpdateStoreInfo(store)
}