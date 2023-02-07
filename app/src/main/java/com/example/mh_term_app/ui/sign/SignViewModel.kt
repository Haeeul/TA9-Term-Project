package com.example.mh_term_app.ui.sign

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.Handler
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import java.util.*
import kotlin.concurrent.timer

class SignViewModel(): ViewModel() {
    // 사용자 입력 내용
    val phoneNumTxt = MutableLiveData<String>("")
    val authNumTxt = MutableLiveData<String>("")

    // 안내 문구
    val authNotice = MutableLiveData<String>()
    val timer = MutableLiveData<String>()

    // 타이머
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

    // 안내 문구 표시
    private val _isValidNotice = MutableLiveData<Boolean>(false)
    val isValidNotice : LiveData<Boolean>
        get() = _isValidNotice

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
            _isValidRequestBtn.value = false
        }else{
            _isValidTimer.value = false
            _isValidNotice.value = false
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

                val str = MHApplication.getApplicationContext().getString(R.string.txt_auth_timeout)
                authNotice.postValue(str)
                _isValidNotice.postValue(true)
                _isValidAuth.postValue(false)
            }
        }
    }

    private fun stopAuthTimer(){
        setTimeVisibility(false)

        minute = 3
        second = 0

        timerTask?.cancel()
    }

    // 인증 번호 자릿수 확인
    fun inputAuthNum(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkAuthNumLength() }, 0L)
    }

    private fun checkAuthNumLength(){
        _isValidAuth.value = authNumTxt.value?.length == 6 && _isValidTimer.value==true
    }

    // 인증 번호 확인
    fun setAuthFail(){
        val test = false
        if(!test){
            _isValidAuth.value = false
            _isValidNotice.value = true

            val str = MHApplication.getApplicationContext().getString(R.string.txt_auth_fail)
            authNotice.value = str
        }
    }

}