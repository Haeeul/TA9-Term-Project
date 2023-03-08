package com.example.mh_term_app.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mh_term_app.data.local.entity.SearchPlace

@Dao
interface SearchPlaceDao {
    @Insert
    suspend fun insertPlace(word: SearchPlace)

    @Query("SELECT * FROM searchplace")
    fun getAllCenter(): LiveData<List<SearchPlace>>

    @Query("DELETE FROM searchplace")
    suspend fun deleteAllCenter()
}