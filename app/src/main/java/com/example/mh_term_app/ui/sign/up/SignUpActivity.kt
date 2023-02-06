package com.example.mh_term_app.ui.sign.up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override val layoutResID: Int = R.layout.activity_sign_up

    fun goToBackListener(view : View){
        finish()
    }
}