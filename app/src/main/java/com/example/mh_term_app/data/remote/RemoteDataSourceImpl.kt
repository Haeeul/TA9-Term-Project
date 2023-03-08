package com.example.mh_term_app.data.remote

import android.util.Log
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.data.model.request.*
import com.example.mh_term_app.data.model.response.*
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
        } catch (e: FirebaseException) {
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
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }
        return valid
    }

    override suspend fun postSignUp(
        id: String,
        password: String,
        nickname: String,
        type: String
    ): Boolean {
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
        } catch (e: FirebaseException) {
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
        } catch (e: FirebaseException) {
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
                    if (valid) {
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
        } catch (e: FirebaseException) {
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
        } catch (e: FirebaseException) {
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
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }

        return result
    }

    override suspend fun getCategoryList(type: String): MutableList<ResponseCategoryPlace> {
        var placeList = mutableListOf<ResponseCategoryPlace>()
        try {
            db.collection("places")
                .whereEqualTo("type", type)
                .get()
                .addOnSuccessListener { result ->
                    for (place in result) {
                        placeList.add(
                            ResponseCategoryPlace(
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
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }
        return placeList
    }

    override suspend fun getPlaceRating(id: String): Float {
        var rating = 0f
        try {
            db.collection("reviews")
                .whereEqualTo("placeId", id)
                .get()
                .addOnSuccessListener { result ->
                    for (item in result) {
                        rating += item.data["rating"].toString().toFloat()
                    }
                    rating /= result.size()
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }.await()
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }
        return rating
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
        } catch (e: FirebaseException) {
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
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }
        return facility
    }

    override suspend fun getChargingInfo(id: String): ResponseChargingStation {
        var charging = ResponseChargingStation()
        try {
            db.collection("places").document(id)
                .get()
                .addOnSuccessListener { result ->
                    charging = result.toObject<ResponseChargingStation>()!!
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }.await()
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }
        return charging
    }

    override suspend fun postUpdateAddress(place: RequestUpdatePlaceAddress): Boolean {
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
        } catch (e: FirebaseException) {
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
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }

        return result
    }

    override suspend fun postUpdateFacilityInfo(facility: RequestUpdateFacilityInfo): Boolean {
        var result = false

        try {
            db.collection("updateInfo")
                .add(facility)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: $documentReference")
                    result = true
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error adding documents.", exception)
                    result = false
                }.await()
        } catch (e: FirebaseException) {
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
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }

        return result
    }

    override suspend fun getReview(placeId: String): MutableList<ResponseReviewList> {
        var reviewList = mutableListOf<ResponseReviewList>()
        try {
            db.collection("reviews")
                .whereEqualTo("placeId", placeId)
                .get()
                .addOnSuccessListener { result ->
                    for (review in result) {
                        reviewList.add(
                            ResponseReviewList(
                                review.id,
                                review.data["placeId"].toString(),
                                review.data["placeName"].toString(),
                                review.data["placeType"].toString(),
                                review.data["userId"].toString(),
                                review.data["userNickname"].toString(),
                                review.data["userType"].toString(),
                                review.data["content"].toString(),
                                review.data["rating"].toString().toFloat(),
                                review.data["likeCount"].toString().toDouble(),
                                review.data["like"] as MutableList<String>?,
                                review.data["date"].toString()
                            )
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }.await()
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }
        return reviewList
    }

    override suspend fun getUserReview(userId: String): MutableList<ResponseReviewList> {
        var reviewList = mutableListOf<ResponseReviewList>()
        try {
            db.collection("reviews")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { result ->
                    for (review in result) {
                        reviewList.add(
                            ResponseReviewList(
                                review.id,
                                review.data["placeId"].toString(),
                                review.data["placeName"].toString(),
                                review.data["placeType"].toString(),
                                review.data["userId"].toString(),
                                review.data["userNickname"].toString(),
                                review.data["userType"].toString(),
                                review.data["content"].toString(),
                                review.data["rating"].toString().toFloat(),
                                review.data["likeCount"].toString().toDouble(),
                                review.data["like"] as MutableList<String>?,
                                review.data["date"].toString()
                            )
                        )
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }.await()
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }
        return reviewList
    }

    override suspend fun postNewUserInfo(id: String, nickname: String, type: String): Boolean {
        var result = false

        try {
            db.collection("users")
                .document(id)
                .update("nickname", nickname)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: $documentReference")
                    result = true
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error adding documents.", exception)
                    result = false
                }.await()
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }

        try {
            db.collection("users")
                .document(id)
                .update("type", type)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: $documentReference")
                    result = true
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error adding documents.", exception)
                    result = false
                }.await()
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }

        return result
    }

    override suspend fun getSearchInfo(name: String): ResponseCategoryPlace {
        var placeInfo = ResponseCategoryPlace("",PlaceInfo())
        try {
            db.collection("places")
                .whereEqualTo("name", name)
                .get()
                .addOnSuccessListener { result ->
                    if(!result.isEmpty){
                        val info = result.documents[0].data!!
                        placeInfo =
                            ResponseCategoryPlace(
                                result.documents[0].id,
                                PlaceInfo(
                                    info["type"].toString(),
                                    info["address"].toString(),
                                    info["latitude"].toString().toDouble(),
                                    info["longitude"].toString().toDouble(),
                                    info["name"].toString(),
                                    info["phone"].toString(),
                                    info["detailType"].toString(),
                                    info["location"].toString()
                                )
                            )
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }.await()
        } catch (e: FirebaseException) {
            Log.e(TAG, e.message.toString())
        }
        return placeInfo
    }
}