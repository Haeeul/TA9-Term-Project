package com.example.mh_term_app.data.repository

import com.example.mh_term_app.data.model.request.RequestPlaceStore
import com.example.mh_term_app.data.model.request.RequestReportFacility
import com.example.mh_term_app.data.remote.RemoteDataSource
import com.example.mh_term_app.data.remote.RemoteDataSourceImpl

class MapRepository {
    private val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    suspend fun postReportStore(store: RequestPlaceStore) = remoteDataSource.postReportStore(store)
    suspend fun postReportFacility(facility: RequestReportFacility) = remoteDataSource.postReportFacility(facility)

    suspend fun getStoreList(type : String) = remoteDataSource.getStoreList(type)
}