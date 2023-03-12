package com.example.mh_term_app.ui.sign.up

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.data.repository.UserRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class UserInfoViewModel : ViewModel() {
    val auth = Firebase.auth
    private val TAG : String = "[UserInfoViewModel] "
    private val userRepository = UserRepository()

    // 사용자 입력 내용
    val nicknameTxt = MutableLiveData<String>()
    val typeTxt = MutableLiveData<String>()

    // 안내 문구
    val nicknameNotice = MutableLiveData<String>()

    // 중복확인 버튼 enable
    private val _isValidNickCheckBtn = MutableLiveData(false)
    val isValidNickCheckBtn : LiveData<Boolean>
        get() = _isValidNickCheckBtn

    // 안내 문구 표시
    private val _isValidNickNotice = MutableLiveData<Boolean>(false)
    val isValidNickNotice : LiveData<Boolean>
        get() = _isValidNickNotice

    // 인증 유효 검사
    private val _isValidNickname = MutableLiveData(false)
    val isValidNickname : LiveData<Boolean>
        get() = _isValidNickname

    // 인증 유효 검사
    private val _isValidSignUp = MutableLiveData<Boolean>()
    val isValidSignUp : LiveData<Boolean>
        get() = _isValidSignUp

    fun setUserData(){
        nicknameTxt.value = MHApplication.prefManager.userNickname
        typeTxt.value = MHApplication.prefManager.userType
    }

    // 닉네임 입력 확인
    fun inputNickname(s: CharSequence?, start: Int, before: Int, count: Int){
        Handler(Looper.getMainLooper()).postDelayed({ checkNicknameLength() }, 0L)
//        Handler(Looper.getMainLooper()).postDelayed({ checkOriginNickname() }, 0L)

        resetValidNickname()
    }

    private fun checkNicknameLength(){
        if(nicknameTxt.value?.length!! == 0){
            _isValidNickCheckBtn.value = false
            _isValidNickNotice.value = false
        }else{
            checkNickForm()
        }
    }


    private fun checkNickForm() {
        nicknameNotice.value = MHApplication.getApplicationContext().getString(R.string.notice_nick_input)
        val nickPattern: Pattern = Pattern.compile("[ㄱ-ㅎ가-힣ㅏ-ㅣa-zA-Z0-9]{0,30}")

        _isValidNickNotice.value = !nickPattern.matcher(nicknameTxt.value.toString()).matches()
        _isValidNickCheckBtn.value = nickPattern.matcher(nicknameTxt.value.toString()).matches()
    }

    fun checkDoubleNickname(){
        viewModelScope.launch {
            _isValidNickname.value = userRepository.getValidateNick(nicknameTxt.value.toString())
            _isValidNickNotice.value = true
        }
    }

    private fun checkOriginNickname(){
        _isValidNickCheckBtn.value = nicknameTxt.value != MHApplication.prefManager.userNickname && nicknameTxt.value!!.isNotEmpty()
    }

    private fun resetValidNickname(){
        _isValidNickname.value = false
        _isValidNickNotice.value = false
    }

    fun setNickDeleteBtnListener(){
        if(_isValidNickname.value == false) nicknameTxt.value = ""
    }

    fun setTypeTxt(txt:String){
        typeTxt.value = txt
    }

    fun postSignUp(id : String, password : String){
        viewModelScope.launch {
            _isValidSignUp.value = userRepository.postSignUp(
                id,
                password,
                nicknameTxt.value.toString(),
                if(typeTxt.value.toString()== "null"){"none"} else {typeTxt.value.toString()}
            )
        }
    }

    private val _isValidUpdateBtn = MutableLiveData<Boolean>()
    val isValidUpdateBtn : LiveData<Boolean>
        get() = _isValidUpdateBtn

    fun checkUpdateBtn(){
        _isValidUpdateBtn.value = _isValidNickname.value == true || MHApplication.prefManager.userType != typeTxt.value && _isValidNickCheckBtn.value == false
                && _isValidNickNotice.value == false

    }

    // 정보 업데이트
    private val _isValidUpdateInfo = MutableLiveData<Boolean>()
    val isValidUpdateInfo : LiveData<Boolean>
        get() = _isValidUpdateInfo

    fun postNewUserInfo(){
        viewModelScope.launch {
            _isValidUpdateInfo.value = userRepository.postNewUserInfo(
                MHApplication.prefManager.userId,
                nicknameTxt.value.toString(),
                if(typeTxt.value.toString()== ""){"none"} else {typeTxt.value.toString()}
            )
        }

    }

}