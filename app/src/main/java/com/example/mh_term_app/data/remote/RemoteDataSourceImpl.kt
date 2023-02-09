package com.example.mh_term_app.data.remote

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class RemoteDataSourceImpl : RemoteDataSource {

    val db = Firebase.firestore
    private val TAG: String = "[RemoteDataSourceImpl] "

    override suspend fun getValidateNick(nickname: String): Boolean {
        var valid = false
        try {
            db.collection("users")
                .whereEqualTo("nickname", nickname)
                .get()
                .addOnSuccessListener { result ->
                    valid = result.isEmpty
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }.await()
        }catch (e:FirebaseException){
            Log.e(TAG, e.message.toString())
        }
        return valid
    }
}