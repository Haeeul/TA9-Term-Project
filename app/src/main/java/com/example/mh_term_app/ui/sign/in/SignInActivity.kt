package com.example.mh_term_app.ui.sign.`in`

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignInBinding
import com.example.mh_term_app.ui.sign.up.SignUpActivity
import com.example.mh_term_app.utils.extension.startActivityWithAffinity
import com.example.mh_term_app.utils.extension.startActivityWithFinish
import com.example.mh_term_app.utils.extension.toast

class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_sign_in
    private val signInViewModel : SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            vm = signInViewModel
            edtSignInId.requestFocus()
        }
    }

    override fun initObserver(){
        signInViewModel.isValidSignIn.observe(this){
            if (it) {
                toast("로그인 성공")
                Log.d("명",MHApplication.prefManager.userId+"/"+MHApplication.prefManager.userPassword+"/"+MHApplication.prefManager.userNickname+"/"+MHApplication.prefManager.userType+"/")
                startActivityWithAffinity(MainActivity::class.java)
            }
        }
    }

    fun goToBackListener(view : View){
        finish()
    }

    fun goToSignUpListener(view : View){
        startActivityWithFinish(SignUpActivity::class.java)
    }
}