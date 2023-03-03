package com.example.mh_term_app.ui.map.details.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReviewViewModel : ViewModel() {
    val reviewTxt = MutableLiveData<String>()
    val rating = MutableLiveData<Float>(0f)

    private val _isValidCompleteBtn = MutableLiveData(false)
    val isValidCompleteBtn : LiveData<Boolean>
        get() = _isValidCompleteBtn

    fun checkCompleteBtn(){
        _isValidCompleteBtn.value = reviewTxt.value?.isNotEmpty() == true && rating.value != 0f
    }
}