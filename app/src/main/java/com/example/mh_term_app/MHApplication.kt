package com.example.mh_term_app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class MHApplication  : Application() {

    companion object {
//        lateinit var prefManager : SharedPreferencesManager
        private var instance: MHApplication? = null
        fun getApplicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
//        prefManager = SharedPreferencesManager(applicationContext)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}