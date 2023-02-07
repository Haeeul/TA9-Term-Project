package com.example.mh_term_app.ui.sign.`in`

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignInBinding
import com.example.mh_term_app.ui.sign.SignViewModel
import com.example.mh_term_app.ui.sign.up.SignUpActivity

class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    override val layoutResID: Int = R.layout.activity_sign_in
    private val signInViewModel : SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            vm = signInViewModel
            edtSignInPhone.requestFocus()
        }
    }

    // 인증요청 버튼
    fun postAuthRequest(view: View){
        signInViewModel.startAuthTimer()
        viewDataBinding.edtSighInAuthNum.requestFocus()
    }

    // 로그인 버튼
    fun checkAuth(view: View){
        signInViewModel.checkAuthNum()
    }

    fun goToBackListener(view : View){
        finish()
    }

    fun goToSignUpListener(view : View){
        val goToSignUpIntent = Intent(this, SignUpActivity::class.java)
        startActivity(goToSignUpIntent)
    }
}