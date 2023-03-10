package com.example.mh_term_app.ui.sign.`in`

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.data.repository.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    private val userRepository = UserRepository()

    // 사용자 입력 내용
    val idTxt = MutableLiveData("")
    val passwordTxt = MutableLiveData("")

    val noticeTxt = MutableLiveData("")

    // 다음 버튼 활성화
    private val _isValidNextBtn = MutableLiveData<Boolean>()
    val isValidNextBtn : LiveData<Boolean>
        get() = _isValidNextBtn

    // 안내 문구 표시
    private val _isValidSignInNotice = MutableLiveData(false)
    val isValidSignInNotice : LiveData<Boolean>
        get() = _isValidSignInNotice

    // 로그인 유효성 검사
    private val _isValidSignIn = MutableLiveData<Boolean>()
    val isValidSignIn : LiveData<Boolean>
        get() = _isValidSignIn

    // 입력값 확인
    fun inputSignInInfo(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkValidNextBtn() }, 0L)

        resetValid()
    }

    private fun resetValid(){
        if(_isValidSignInNotice.value == true){
            _isValidSignInNotice.value = false
            _isValidNextBtn.value = false
        }
    }

    // 다음 버튼 활성화 확인
    private fun checkValidNextBtn(){
        _isValidNextBtn.value = idTxt.value?.isNotEmpty() == true && passwordTxt.value?.length!! >= 8
    }

    fun checkValidSignIn(){
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(idTxt.value.toString()).matches()){
            noticeTxt.value = MHApplication.getApplicationContext().getString(R.string.notice_id_form)
            _isValidSignInNotice.value = true
        }else{
            noticeTxt.value = MHApplication.getApplicationContext().getString(R.string.notice_sign_in_fail)

            viewModelScope.launch {
                _isValidSignIn.value = userRepository.postSignIn(idTxt.value.toString(), passwordTxt.value.toString())

                if(_isValidSignIn.value == false) _isValidSignInNotice.value = true
            }
        }

    }
}