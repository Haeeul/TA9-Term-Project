package com.example.mh_term_app.ui.sign.up

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignUpBinding
import com.example.mh_term_app.utils.extension.clearStartActivity
import com.example.mh_term_app.utils.extension.setKeyboardObserver
import com.example.mh_term_app.utils.extension.setSingleOnClickListener


class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_sign_up
    private val signUpViewModel: SignUpViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            vm = signUpViewModel
            edtSignUpId.requestFocus()
        }
    }

    override fun initView() {
        super.initView()

        binding.tbSignUp.apply {
            title = MHApplication.getApplicationContext().getString(R.string.txt_sign_up)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }
    }

    override fun initObserver(){
        signUpViewModel.isValidId.observe(this){
            signUpViewModel.checkValidNextBtn()

            if(it) binding.edtSignUpPassword.requestFocus()
        }

        signUpViewModel.passwordTxt.observe(this){
            signUpViewModel.checkValidNextBtn()
        }

        signUpViewModel.checkPasswordTxt.observe(this){
            signUpViewModel.checkValidNextBtn()
        }

        setKeyboardObserver(signUpViewModel.isValidNextBtn, false)
    }

    fun goToBackListener(view: View) {
        finish()
    }

    fun goToUserInfoListener(view: View){
        signUpViewModel.addUserInfo()
        this.clearStartActivity(UserInfoActivity::class.java)
    }
}
