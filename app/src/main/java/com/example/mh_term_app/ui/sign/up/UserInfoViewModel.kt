package com.example.mh_term_app.ui.sign.up

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.data.repository.UserRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class UserInfoViewModel : ViewModel() {
    val db = Firebase.firestore
    private val TAG : String = "[UserInfoViewModel] "
    private val userRepository = UserRepository()

    // 사용자 입력 내용
    val nicknameTxt = MutableLiveData<String>()

    // 안내 문구
    val nicknameNotice = MutableLiveData<String>()

    // 중복확인 버튼 enable
    private val _isValidNickCheckBtn = MutableLiveData<Boolean>(false)
    val isValidNickCheckBtn : LiveData<Boolean>
        get() = _isValidNickCheckBtn

    // 안내 문구 표시
    private val _isValidNickNotice = MutableLiveData<Boolean>()
    val isValidNickNotice : LiveData<Boolean>
        get() = _isValidNickNotice

    // 인증 유효 검사
    private val _isValidNickname = MutableLiveData<Boolean>(false)
    val isValidNickname : LiveData<Boolean>
        get() = _isValidNickname

    // 닉네임 입력 확인
    fun inputNickname(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkNicknameLength() }, 0L)

        resetValidNickname()
    }

    private fun checkNicknameLength(){
        _isValidNickCheckBtn.value = nicknameTxt.value?.length!! > 0
    }

    fun checkDoubleNickname(){
        viewModelScope.launch {
            _isValidNickname.value = userRepository.getValidateNick(nicknameTxt.value.toString())
            _isValidNickNotice.value = true
        }
    }

    private fun resetValidNickname(){
        if(_isValidNickname.value!!){
            _isValidNickname.value = false
            _isValidNickNotice.value = false
        }
    }

}