package com.example.mh_term_app.data.repository

import com.example.mh_term_app.data.model.request.RequestReview
import com.example.mh_term_app.data.remote.RemoteDataSource
import com.example.mh_term_app.data.remote.RemoteDataSourceImpl

class ReviewRepository {
    private val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    suspend fun postReview(review: RequestReview) = remoteDataSource.postReview(review)
    suspend fun getReviewList(placeId : String) = remoteDataSource.getReview(placeId)
    suspend fun getUserReviewList(userId : String) = remoteDataSource.getUserReview(userId)
}