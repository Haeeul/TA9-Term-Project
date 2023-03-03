package com.example.mh_term_app.data.remote

import android.util.Log
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.data.model.request.*
import com.example.mh_term_app.data.model.response.PlaceInfo
import com.example.mh_term_app.data.model.response.ResponseCategoryList
import com.example.mh_term_app.data.model.response.ResponseUser
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class RemoteDataSourceImpl : RemoteDataSource {

    private val db = Firebase.firestore
    private val TAG: String = "[RemoteDataSourceImpl] "

    override suspend fun getValidatePhone(phoneNum: String): Boolean {
        var valid = false
        try {
            db.collection("users")
                .whereEqualTo("phoneNum", phoneNum)
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

    override suspend fun postSignUp(id: String, password: String, nickname: String, type: String) : Boolean {
        var result = false

        val user = hashMapOf(
            "id" to id,
            "password" to password,
            "nickname" to nickname,
            "type" to type
        )

        try {
            db.collection("users")
                .document(id)
                .set(user)
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

    override suspend fun getValidateId(id: String): Boolean {
        var valid = false
        try {
            db.collection("users")
                .whereEqualTo("id", id)
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

    override suspend fun postSignIn(id: String, password: String): Boolean {
        var valid = false
        try {
            db.collection("users").document(id)
                .get()
                .addOnSuccessListener { result ->
                    val user = result.toObject<ResponseUser>()
                    valid = user?.password == password
                    if(valid) {
                        if (user != null) {
                            MHApplication.prefManager.userId = user.id
                            MHApplication.prefManager.userPassword = user.password
                            MHApplication.prefManager.userNickname = user.nickname
                            MHApplication.prefManager.userType = user.type
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }.await()
        }catch (e:FirebaseException){
            Log.e(TAG, e.message.toString())
        }
        return valid
    }

    override suspend fun postReportStore(store: RequestPlaceStore): Boolean {
        var result = false

        try {
            db.collection("places")
                .add(store)
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

    override suspend fun postReportFacility(facility: RequestPlaceFacility): Boolean {
        var result = false

        try {
            db.collection("places")
                .add(facility)
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

    override suspend fun getCategoryList(type: String): MutableList<ResponseCategoryList> {
        var placeList = mutableListOf<ResponseCategoryList>()
        try {
            db.collection("places")
                .whereEqualTo("type", type)
                .get()
                .addOnSuccessListener { result ->
                    for(place in result){
                        placeList.add(
                            ResponseCategoryList(
                                place.id,
                                PlaceInfo(
                                    place.data["type"].toString(),
                                    place.data["address"].toString(),
                                    place.data["latitude"].toString().toDouble(),
                                    place.data["longitude"].toString().toDouble(),
                                    place.data["name"].toString(),
                                    place.data["phone"].toString(),
                                    place.data["detailType"].toString(),
                                    place.data["location"].toString()
                                )
                            )
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }.await()
        }catch (e:FirebaseException){
            Log.e(TAG, e.message.toString())
        }
        return placeList
    }

    override suspend fun getStoreInfo(id: String): RequestPlaceStore {
        var store = RequestPlaceStore()
        try {
            db.collection("places").document(id)
                .get()
                .addOnSuccessListener { result ->
                    store = result.toObject<RequestPlaceStore>()!!
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }.await()
        }catch (e:FirebaseException){
            Log.e(TAG, e.message.toString())
        }
        return store
    }

    override suspend fun getFacilityInfo(id: String): RequestPlaceFacility {
        var facility = RequestPlaceFacility()
        try {
            db.collection("places").document(id)
                .get()
                .addOnSuccessListener { result ->
                    facility = result.toObject<RequestPlaceFacility>()!!
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }.await()
        }catch (e:FirebaseException){
            Log.e(TAG, e.message.toString())
        }
        return facility
    }

    override suspend fun postUpdateAddress(place : RequestUpdatePlaceAddress): Boolean {
        var result = false

        try {
            db.collection("updateInfo")
                .add(place)
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

    override suspend fun postUpdateStoreInfo(store: RequestUpdateStoreInfo): Boolean {
        var result = false

        try {
            db.collection("updateInfo")
                .add(store)
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

    override suspend fun postReview(review: RequestReview): Boolean {
        var result = false

        try {
            db.collection("reviews")
                .add(review)
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