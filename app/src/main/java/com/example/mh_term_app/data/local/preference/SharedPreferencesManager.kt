package com.example.mh_term_app.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SharedPreferencesManager(context: Context) {

    val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

    var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var userIdx : Int
        get() = sharedPreferences.getInt(USER_IDX, 0)
        set(value) = sharedPreferences.putInt(USER_IDX, value)

    var userId: String
        get() = sharedPreferences.getString(USER_ID)
        set(value) = sharedPreferences.putString(USER_ID, value)

    var userPassword: String
        get() = sharedPreferences.getString(USER_PASSWORD)
        set(value) = sharedPreferences.putString(USER_PASSWORD, value)

    var userNickname: String
        get() = sharedPreferences.getString(USER_NICKNAME)
        set(value) = sharedPreferences.putString(USER_NICKNAME, value)

    var userType: String
        get() = sharedPreferences.getString(USER_TYPE)
        set(value) = sharedPreferences.putString(USER_TYPE, value)

    companion object {
        const val PREFERENCES_KEY = "preferences"
        private const val USER_IDX = "user_idx"
        private const val USER_ID = "user_email"
        private const val USER_PASSWORD = "user_password"
        private const val USER_NICKNAME = "user_nickname"
        private const val USER_TYPE = "user_gender"

        private fun SharedPreferences.getString(key: String) =
            getString(key, "").orEmpty()

        private fun SharedPreferences.putString(key: String, value: String) =
            edit().putString(key, value).apply()

        private fun SharedPreferences.putInt(key: String, value: Int) =
            edit().putInt(key, value).apply()
    }
}