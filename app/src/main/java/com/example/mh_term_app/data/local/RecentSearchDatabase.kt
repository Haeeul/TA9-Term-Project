package com.example.mh_term_app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mh_term_app.data.local.RecentSearchDatabase.Companion.DB_VERSION
import com.example.mh_term_app.data.local.dao.RecentSearchDao
import com.example.mh_term_app.data.local.entity.RecentSearch

@Database(entities = [RecentSearch::class], version = DB_VERSION)
abstract class RecentSearchDatabase : RoomDatabase() {
    abstract fun searchPlaceDao(): RecentSearchDao

    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "search.db"

        @Volatile
        private var INSTANCE: RecentSearchDatabase? = null

        fun getInstance(context: Context): RecentSearchDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context).also { INSTANCE = it }
            }

        private fun build(context: Context) =
            Room.databaseBuilder(context.applicationContext, RecentSearchDatabase::class.java, DB_NAME)
                .build()
    }
}