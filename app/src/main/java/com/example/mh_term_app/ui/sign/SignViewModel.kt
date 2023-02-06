package com.example.mh_term_app.ui.sign

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.Handler

class SignViewModel(): ViewModel() {
    // 사용자 입력 내용
    val phoneNumTxt = MutableLiveData<String>("")

    // 중복 확인 enable
    private val _isValidRequestBtn = MutableLiveData<Boolean>(false)
    val isValidRequestBtn : LiveData<Boolean>
        get() = _isValidRequestBtn

    // 휴대폰 번호 자릿수 확인
    fun inputPhoneNum(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkPhoneLength() }, 0L)
    }

    private fun checkPhoneLength(){
        _isValidRequestBtn.value = phoneNumTxt.value?.length == 11
    }
}