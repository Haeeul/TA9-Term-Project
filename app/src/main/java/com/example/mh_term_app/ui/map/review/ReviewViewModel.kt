package com.example.mh_term_app.ui.map.review

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.data.model.request.RequestReview
import com.example.mh_term_app.data.model.response.ResponseReviewList
import com.example.mh_term_app.data.repository.ReviewRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class ReviewViewModel : ViewModel() {
    private val reviewRepository = ReviewRepository()

    val reviewTxt = MutableLiveData<String>()
    val rating = MutableLiveData<Float>(0f)

    private val _isValidCompleteBtn = MutableLiveData(false)
    val isValidCompleteBtn : LiveData<Boolean>
        get() = _isValidCompleteBtn

    private val _isValidReview = MutableLiveData<Boolean>()
    val isValidReview : LiveData<Boolean>
        get() = _isValidReview

    private val _reviewList = MutableLiveData<MutableList<ResponseReviewList>>()
    val reviewList : LiveData<MutableList<ResponseReviewList>>
        get() = _reviewList

    private val _isValidReviewList = MutableLiveData(false)
    val isValidReviewList : LiveData<Boolean>
        get() = _isValidReviewList

    fun checkCompleteBtn(){
        _isValidCompleteBtn.value = reviewTxt.value?.isNotEmpty() == true && rating.value != 0f
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun postReview(placeId: String, placeType : String, placeName : String){
        viewModelScope.launch {
            val review = RequestReview(
                placeId = placeId,
                placeName = placeName,
                placeType = placeType,
                userId = MHApplication.prefManager.userId,
                userNickname = MHApplication.prefManager.userNickname,
                userType = MHApplication.prefManager.userType,
                content = reviewTxt.value.toString(),
                rating = rating.value.toString().toDouble(),
                likeCount = 0.0,
                like = null,
                date = LocalDate.now().toString()
            )

            _isValidReview.value = reviewRepository.postReview(review)
        }
    }

    fun getReviewList(placeId:String){
        viewModelScope.launch {
            _reviewList.value = reviewRepository.getReviewList(placeId)

            _isValidReviewList.value = _reviewList.value!!.size > 0
        }
    }

    private val _userReviewList = MutableLiveData<MutableList<ResponseReviewList>>()
    val userReviewList : LiveData<MutableList<ResponseReviewList>>
        get() = _userReviewList

    private val _isValidUserReviewList = MutableLiveData(false)
    val isValidUserReviewList : LiveData<Boolean>
        get() = _isValidUserReviewList

    fun getUserReview(){
        viewModelScope.launch {
            _userReviewList.value = reviewRepository.getUserReviewList(MHApplication.prefManager.userId)

            _isValidUserReviewList.value = _userReviewList.value!!.size > 0
        }
    }
}