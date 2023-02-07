package com.example.mh_term_app.ui.sign.up

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignUpBinding
import com.example.mh_term_app.ui.sign.SignViewModel

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override val layoutResID: Int = R.layout.activity_sign_up
    private val signUpViewModel : SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            vm = signUpViewModel
            edtSignUpPhone.requestFocus()
        }
    }

    // 인증요청 버튼
    fun postSignUpAuthRequest(view: View){
        signUpViewModel.startAuthTimer()
        viewDataBinding.edtSignUpAuthNum.requestFocus()
    }

    // 회원가입 버튼
    fun checkSignUpAuth(view: View){
        signUpViewModel.setAuthFail()
    }

    fun goToBackListener(view : View){
        finish()
    }
}