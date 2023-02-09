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

    override suspend fun postSignUp(phoneNum: String, nickname: String, type: String) : Boolean {
        var result = false

        val user = hashMapOf(
            "phoneNum" to phoneNum,
            "nickname" to nickname,
            "type" to type
        )

        try {
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    result = true
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error adding documents.", exception)
                    result = false
                }.await()
        }catch (e:FirebaseException){
            Log.e(TAG, e.message.toString())
        }

        return result
    }
}