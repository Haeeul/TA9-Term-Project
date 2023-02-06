package com.example.mh_term_app.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignInHomeBinding
import com.example.mh_term_app.ui.sign.`in`.SignInActivity
import com.example.mh_term_app.ui.sign.up.SignUpActivity

class SignInHomeActivity : BaseActivity<ActivitySignInHomeBinding>() {
    override val layoutResID: Int = R.layout.activity_sign_in_home

    fun goToSignInListener(view : View){
        val goToSignInIntent = Intent(this, SignInActivity::class.java)
        startActivity(goToSignInIntent)
    }

    fun goToSignUpListener(view : View){
        val goToSignUpIntent = Intent(this, SignUpActivity::class.java)
        startActivity(goToSignUpIntent)
    }
}