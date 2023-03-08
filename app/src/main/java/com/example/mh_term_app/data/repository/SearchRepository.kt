package com.example.mh_term_app.data.repository

import androidx.lifecycle.LiveData
import com.example.mh_term_app.data.local.dao.SearchPlaceDao
import com.example.mh_term_app.data.local.entity.SearchPlace

class SearchRepository(private val dao: SearchPlaceDao) {
    val places : LiveData<List<SearchPlace>> = dao.getAllCenter()

    suspend fun insert(place: SearchPlace) {dao.insertPlace(place)}
    suspend fun deleteAll() {dao.deleteAllCenter()}
}