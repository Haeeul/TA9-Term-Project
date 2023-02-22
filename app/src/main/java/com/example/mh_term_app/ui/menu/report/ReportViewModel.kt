package com.example.mh_term_app.ui.menu.report

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReportViewModel : ViewModel() {
    // 사용자 입력 내용
    val addressTxt = MutableLiveData<String>()
    val typeTxt = MutableLiveData<String>()

    // 다음 버튼 활성화
    private val _isValidNextBtn = MutableLiveData(false)
    val isValidNextBtn : LiveData<Boolean>
        get() = _isValidNextBtn

    fun setTypeTxt(txt:String){
        typeTxt.value = txt
    }

    // 주소 입력 확인
    fun inputAddress(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkValidNextBtn() }, 0L)
    }

    fun checkValidNextBtn(){
        _isValidNextBtn.value = addressTxt.value?.isNotEmpty() == true &&  typeTxt.value?.isNotEmpty() == true
    }
}