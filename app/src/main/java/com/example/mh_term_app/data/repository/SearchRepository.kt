package com.example.mh_term_app.data.repository

import androidx.lifecycle.LiveData
import com.example.mh_term_app.data.local.dao.RecentSearchDao
import com.example.mh_term_app.data.local.entity.RecentSearch

class SearchRepository(private val dao: RecentSearchDao) {
    val places : LiveData<List<RecentSearch>> = dao.getAllCenter()

    suspend fun insert(place: RecentSearch) {dao.insertPlace(place)}
    suspend fun delete(placeId : String) {dao.deletePlace(placeId)}
    suspend fun deleteAll() {dao.deleteAllCenter()}
}