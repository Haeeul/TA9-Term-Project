package com.example.mh_term_app.ui.sign

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.Handler
import java.util.*
import kotlin.concurrent.timer

class SignViewModel(): ViewModel() {
    // 사용자 입력 내용
    val phoneNumTxt = MutableLiveData<String>("")

    // 안내 문구
    val authNotice = MutableLiveData<String>()
    val timer = MutableLiveData<String>()

    var minute = 3
    var second = 0
    private var timerTask: Timer? = null

    // 중복 확인 enable
    private val _isValidRequestBtn = MutableLiveData<Boolean>(false)
    val isValidRequestBtn : LiveData<Boolean>
        get() = _isValidRequestBtn

    // 타이머 표시
    private val _isValidTimer = MutableLiveData<Boolean>(false)
    val isValidTimer : LiveData<Boolean>
        get() = _isValidTimer

    // 인증 유효 검사
    private val _isValidAuth = MutableLiveData<Boolean>(false)
    val isValidAuth : LiveData<Boolean>
        get() = _isValidAuth

    // 휴대폰 번호 자릿수 확인
    fun inputPhoneNum(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkPhoneLength() }, 0L)
    }

    private fun checkPhoneLength(){
        if(phoneNumTxt.value?.length == 11) {
            _isValidRequestBtn.value = true
        }else{
            _isValidRequestBtn.value = false
            stopAuthTimer()
        }
    }

    private fun setTimeVisibility(visibility : Boolean){
        if(visibility){
            _isValidTimer.value = true
        }else{
            _isValidTimer.value = false
            _isValidAuth.value = false
        }
    }

    fun startAuthTimer(){
        setTimeVisibility(true)

        timerTask = timer(period = 1000){

            if(second == 0){
                minute--
                second = 60;
            }

            second--

            timer.postValue(String.format("%02d:%02d",minute,second))

            if(minute==0&&second==0) {
                this.cancel()
                authNotice.postValue("인증 번호가 만료되었습니다.")
                _isValidAuth.postValue(true)
            }
        }
    }

    private fun stopAuthTimer(){
        setTimeVisibility(false)

        minute = 3
        second = 0

        timerTask?.cancel()
    }
}