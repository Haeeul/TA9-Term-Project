package com.example.mh_term_app.ui.sign

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.data.repository.UserRepository
import java.util.*

class SignViewModel(): ViewModel() {
    private val userRepository = UserRepository()

    // 사용자 입력 내용
    val emailTxt = MutableLiveData("")
    val passwordTxt = MutableLiveData("")

    // 안내 문구
    val emailNotice = MutableLiveData("")
    val passwordNotice = MutableLiveData("")

    // 이메일 유효성 검사
    private val _isValidEmail = MutableLiveData<Boolean>()
    val isValidEmail : LiveData<Boolean>
        get() = _isValidEmail

    // 이메일 안내 문구 표시
    private val _isValidEmailNotice = MutableLiveData(false)
    val isValidEmailNotice : LiveData<Boolean>
        get() = _isValidEmailNotice

    // 비밀번호 유효성 검사
    private val _isValidPassword = MutableLiveData<Boolean>()
    val isValidPassword : LiveData<Boolean>
        get() = _isValidPassword

    // 이메일 안내 문구 표시
    private val _isValidPasswordNotice = MutableLiveData(false)
    val isValidPasswordNotice : LiveData<Boolean>
        get() = _isValidPasswordNotice

    // 인증 유효 검사
    private val _isValidAuth = MutableLiveData(false)
    val isValidAuth : LiveData<Boolean>
        get() = _isValidAuth

    // 이메일 입력칸 확인
    fun inputEmail(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkEmailForm() }, 0L)
        Handler(Looper.getMainLooper()).postDelayed({ checkEmptyEmail() }, 0L)
    }

    // 이메일 형식 확인
    private fun checkEmailForm() {
        emailNotice.value = MHApplication.getApplicationContext().getString(R.string.txt_email_form_check)
        _isValidEmailNotice.value = !android.util.Patterns.EMAIL_ADDRESS.matcher(emailTxt.value.toString()).matches()

//        if(_isValidEmailNotice.value == true){
//            _isValidEmailBtn.postValue(false)
//        }else{
//            _isValidEmailBtn.postValue(true)
//        }
    }

    // 이메일 빈칸확인
    private fun checkEmptyEmail(){
        if(emailTxt.value?.length == 0){
            _isValidEmailNotice.postValue(false)
        }
    }

    private fun resetValidateEmail(){
        if(_isValidEmail.value == true){
            _isValidEmail.postValue(false)
        }
    }

    // 비밀번호 자릿수 확인
    fun inputPassword(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkAuthNumLength() }, 0L)
    }

    private fun checkAuthNumLength(){
        _isValidAuth.value = passwordTxt.value?.length == 6 && _isValidTimer.value==true && emailNotice.value !=1
    }

    // 비밀번호 일치 확인
    fun setAuthFail(){
        val test = false
        if(!test){
            _isValidAuth.value = false
            _isValidEmailNotice.value = true

            emailNotice.value = 2
        }
    }

}