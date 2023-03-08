package com.example.mh_term_app.ui.sign.`in`

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignInBinding
import com.example.mh_term_app.ui.sign.up.SignUpActivity
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.extension.startActivityWithAffinity
import com.example.mh_term_app.utils.extension.startActivityWithFinish
import com.example.mh_term_app.utils.extension.toast

class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_sign_in
    private val signInViewModel : SignInViewModel by viewModels()

    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            vm = signInViewModel
            edtSignInId.requestFocus()
        }

        type = intent.getStringExtra("type").toString()
    }

    override fun initView() {
        super.initView()

        binding.tbSignIn.apply {
            title = getString(R.string.title_sign_in)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }
    }

    override fun initObserver(){
        signInViewModel.isValidSignIn.observe(this){
            if (it) {
                toast("로그인 성공")
                if(type == "login") finish()
                else startActivityWithAffinity(MainActivity::class.java)
            }
        }
    }

    fun goToSignUpListener(view : View){
        startActivityWithFinish(SignUpActivity::class.java)
    }
}