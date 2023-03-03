package com.example.mh_term_app.ui.map.details.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.data.model.request.RequestReview
import com.example.mh_term_app.data.repository.ReviewRepository
import kotlinx.coroutines.launch

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

    fun checkCompleteBtn(){
        _isValidCompleteBtn.value = reviewTxt.value?.isNotEmpty() == true && rating.value != 0f
    }

    fun postReview(placeId: String, placeType : String, placeName : String){
        viewModelScope.launch {
            val review = RequestReview(
                placeId = placeId,
                placeName = placeName,
                placeType = placeType,
                writer = MHApplication.prefManager.userId,
                writerType = MHApplication.prefManager.userType,
                content = reviewTxt.value.toString(),
                rating = rating.value.toString().toDouble(),
                likeCount = 0.0,
                like = null
            )

            _isValidReview.value = reviewRepository.postReview(review)
        }
    }
}