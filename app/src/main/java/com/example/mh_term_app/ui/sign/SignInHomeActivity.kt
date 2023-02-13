package com.example.mh_term_app.ui.sign

import android.view.View
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignInHomeBinding
import com.example.mh_term_app.ui.sign.`in`.SignInActivity
import com.example.mh_term_app.ui.sign.up.SignUpActivity
import com.example.mh_term_app.utils.extension.clearStartActivity
import com.example.mh_term_app.utils.extension.startActivityWithAffinity

class SignInHomeActivity : BaseActivity<ActivitySignInHomeBinding>() {
    override val layoutResID: Int = R.layout.activity_sign_in_home

    fun goToSignInListener(view : View){
        clearStartActivity(SignInActivity::class.java)
    }

    fun goToSignUpListener(view : View){
        clearStartActivity(SignUpActivity::class.java)
    }

    fun goToMapListener(view: View){
        startActivityWithAffinity(MainActivity::class.java)
    }
}