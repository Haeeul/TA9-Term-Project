package com.example.mh_term_app.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mh_term_app.data.local.entity.RecentSearch

@Dao
interface RecentSearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(word: RecentSearch)

    @Query("DELETE FROM recentsearch WHERE placeId = :placeId")
    suspend fun deletePlace(placeId: String)

    @Query("SELECT * FROM recentsearch ORDER BY data DESC")
    fun getAllCenter(): LiveData<List<RecentSearch>>

    @Query("DELETE FROM recentsearch")
    suspend fun deleteAllCenter()
}