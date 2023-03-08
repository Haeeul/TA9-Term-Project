package com.example.mh_term_app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mh_term_app.data.local.SearchPlaceDatabase.Companion.DB_VERSION
import com.example.mh_term_app.data.local.dao.SearchPlaceDao
import com.example.mh_term_app.data.local.entity.SearchPlace

@Database(entities = [SearchPlace::class], version = DB_VERSION)
abstract class SearchPlaceDatabase : RoomDatabase() {
    abstract fun searchPlaceDao(): SearchPlaceDao

    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "search.db"

        @Volatile
        private var INSTANCE: SearchPlaceDatabase? = null

        fun getInstance(context: Context): SearchPlaceDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context).also { INSTANCE = it }
            }

        private fun build(context: Context) =
            Room.databaseBuilder(context.applicationContext, SearchPlaceDatabase::class.java, DB_NAME)
                .build()
    }
}