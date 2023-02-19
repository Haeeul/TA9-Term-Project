package com.example.mh_term_app.ui.sign

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.data.repository.UserRepository
import kotlinx.coroutines.launch

class SignViewModel : ViewModel() {
    private val userRepository = UserRepository()

    // 사용자 입력 내용
    val idTxt = MutableLiveData("")
    val passwordTxt = MutableLiveData("")
    val checkPasswordTxt = MutableLiveData("")

    // 안내 문구
    val idNotice = MutableLiveData("")
    val passwordNotice = MutableLiveData("")
    val checkPasswordNotice = MutableLiveData("")

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

    // 인증 유효 검사
    private val _isValidAuth = MutableLiveData(false)
    val isValidAuth : LiveData<Boolean>
        get() = _isValidAuth

    // ID 입력칸 확인
    fun inputId(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkVaildIdBtn() }, 0L)
    }

    // ID 입력칸 빈칸확인
    private fun checkVaildIdBtn(){
        _isValidIdBtn.value = idTxt.value?.length != 0
    }

    fun checkValidId(){
        viewModelScope.launch {
            _isValidId.value = userRepository.getValidateId(idTxt.value.toString())
            _isValidIdNotice.value = true
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

//                if(!_againPasswordForm.value!!) _againPasswordForm.postValue(true)
            }
        }
    }

    // 비밀번호 일치 확인
    fun inputCheckPassword(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkPasswordEqual() }, 0L)
    }

    private fun checkPasswordEqual(){
        _isValidCheckPasswordNotice.value = passwordTxt.value != checkPasswordTxt.value
    }

}