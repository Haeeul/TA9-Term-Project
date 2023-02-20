package com.example.mh_term_app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.mh_term_app.data.local.preference.SharedPreferencesManager

class MHApplication  : Application() {

    companion object {
        lateinit var prefManager : SharedPreferencesManager
        private var instance: MHApplication? = null
        fun getApplicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefManager = SharedPreferencesManager(applicationContext)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}