package com.example.managerapplication.data.remote

import android.util.Log
import com.example.managerapplication.data.model.request.RequestChargingStation
import com.example.managerapplication.data.model.request.RequestMovementCenter
import com.example.managerapplication.data.model.request.RequestPublicToilet
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class RemoteDataSourceImpl : RemoteDataSource {
    private val db = Firebase.firestore
    private val TAG: String = "[RemoteDataSourceImpl] "
    override suspend fun postChargingStation(charging: RequestChargingStation): Boolean {
        var result = false

        try {
            db.collection("places")
                .add(charging)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: $documentReference")
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

    override suspend fun postCenterList(center: RequestMovementCenter): Boolean {
        var result = false

        try {
            db.collection("places")
                .add(center)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: $documentReference")
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

    override suspend fun postToiletList(toilet: RequestPublicToilet): Boolean {
        var result = false

        try {
            db.collection("places")
                .add(toilet)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: $documentReference")
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