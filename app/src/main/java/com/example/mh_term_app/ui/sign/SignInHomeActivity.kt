package com.example.mh_term_app.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.mh_term_app.R
import com.example.mh_term_app.databinding.ActivitySignInHomeBinding
import com.example.mh_term_app.ui.sign.`in`.SignInActivity
import com.example.mh_term_app.ui.sign.up.SignUpActivity

class SignInHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in_home)
        binding.lifecycleOwner = this

    }

    fun goToSignInListener(view : View){
        val goToSignInIntent = Intent(this, SignInActivity::class.java)
        startActivity(goToSignInIntent)
    }

    fun goToSignUpListener(view : View){
        val goToSignUpIntent = Intent(this, SignUpActivity::class.java)
        startActivity(goToSignUpIntent)
    }
}