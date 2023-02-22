package com.example.mh_term_app.data.repository

import com.example.mh_term_app.data.model.response.RequestReportStore
import com.example.mh_term_app.data.remote.RemoteDataSource
import com.example.mh_term_app.data.remote.RemoteDataSourceImpl

class MapRepository {
    private val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    suspend fun postReportStore(store: RequestReportStore) = remoteDataSource.postReportStore(store)
}