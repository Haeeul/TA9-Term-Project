package com.example.mh_term_app.ui.sign.up

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.data.repository.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val userRepository = UserRepository()

    // 사용자 입력 내용
    val idTxt = MutableLiveData("")
    val passwordTxt = MutableLiveData("")
    val checkPasswordTxt = MutableLiveData("")

    // 안내 문구
    val idNotice = MutableLiveData("")

    // ID 중복확인 - 버튼 표시
    private val _isValidIdBtn = MutableLiveData(false)
    val isValidIdBtn : LiveData<Boolean>
        get() = _isValidIdBtn

    // ID 유효성 검사
    private val _isValidId = MutableLiveData<Boolean>()
    val isValidId : LiveData<Boolean>
        get() = _isValidId

    // ID 중복확인 - 안내 문구 표시
    private val _isValidIdNotice = MutableLiveData(false)
    val isValidIdNotice : LiveData<Boolean>
        get() = _isValidIdNotice

    // 비밀번호 유효성 검사
    private val _isValidPassword = MutableLiveData<Boolean>()
    val isValidPassword : LiveData<Boolean>
        get() = _isValidPassword

    // 비밀번호 안내 문구 표시
    private val _isValidPasswordNotice = MutableLiveData(false)
    val isValidPasswordNotice : LiveData<Boolean>
        get() = _isValidPasswordNotice

    // 확인 비밀번호 안내 문구 표시
    private val _isValidCheckPasswordNotice = MutableLiveData(false)
    val isValidCheckPasswordNotice : LiveData<Boolean>
        get() = _isValidCheckPasswordNotice

    // 다음 버튼 활성화
    private val _isValidNextBtn = MutableLiveData(false)
    val isValidNextBtn : LiveData<Boolean>
        get() = _isValidNextBtn

    // ID 입력칸 확인
    fun inputId(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkValidIdBtn() }, 0L)

        resetValidId()
    }

    // ID 입력칸 빈칸확인
    private fun checkValidIdBtn(){
        _isValidIdBtn.value = idTxt.value?.length != 0
    }

    fun checkValidId(){
        viewModelScope.launch {
            _isValidId.value = userRepository.getValidateId(idTxt.value.toString())
            _isValidIdNotice.value = true
        }
    }

    private fun resetValidId(){
        if(_isValidIdNotice.value == true){
            _isValidIdNotice.value = false
            _isValidIdBtn.value = true
            _isValidId.value = false
        }
    }

    // 비밀번호 자릿수 확인
    fun inputPassword(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkPasswordForm() }, 0L)
    }

    private fun checkPasswordForm() {
        when(passwordTxt.value?.length){
            0 -> _isValidPasswordNotice.value = false
            in 1..3 -> {
                _isValidPasswordNotice.value = true
                _isValidPassword.value = false
            }
            else -> {
                _isValidPasswordNotice.value = false
                _isValidPassword.value = true
            }
        }
        checkPasswordEqual()
    }

    // 비밀번호 일치 확인
    fun inputCheckPassword(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkPasswordEqual() }, 0L)
    }

    private fun checkPasswordEqual(){
        _isValidCheckPasswordNotice.value = passwordTxt.value != checkPasswordTxt.value

        when {
            checkPasswordTxt.value == passwordTxt.value -> {
                _isValidCheckPasswordNotice.value = false
            }
            checkPasswordTxt.value?.length == 0 -> {
                _isValidCheckPasswordNotice.value = false
            }
            else -> {
                _isValidCheckPasswordNotice.value = true
            }
        }
    }

    // 다음 버튼 활성화 확인
    fun checkValidNextBtn(){
        _isValidNextBtn.value = _isValidId.value == true && passwordTxt.value?.length!! >= 4 && passwordTxt.value == checkPasswordTxt.value
    }

    // data 저장
    fun addUserInfo(){
        MHApplication.prefManager.userId = idTxt.value.toString()
        MHApplication.prefManager.userPassword = passwordTxt.value.toString()
    }
}