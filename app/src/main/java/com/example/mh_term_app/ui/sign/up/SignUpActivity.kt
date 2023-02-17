package com.example.mh_term_app.ui.sign.up

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignUpBinding
import com.example.mh_term_app.ui.sign.SignViewModel
import com.example.mh_term_app.ui.sign.`in`.SignInActivity
import com.example.mh_term_app.utils.etc.FirebaseAuth.auth
import com.example.mh_term_app.utils.etc.FirebaseAuth.getPhoneNumber
import com.example.mh_term_app.utils.etc.FirebaseAuth.requestPhoneAuth
import com.example.mh_term_app.utils.etc.FirebaseAuth.resendAuthCode
import com.example.mh_term_app.utils.extension.createListenerDialog
import com.example.mh_term_app.utils.extension.startActivityWithFinish
import com.example.mh_term_app.utils.extension.toast
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_sign_up
    private val signUpViewModel: SignViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            vm = signUpViewModel
            edtSignUpPhone.requestFocus()
        }

        initObserver()
    }

    private fun initObserver(){
        signUpViewModel.isValidEmail.observe(this){
            if(it){ // 가입 이력이 없는 유저 : 인증 요청 진행
                requestPhoneAuth(this, getPhoneNumber(binding.edtSignUpPhone.text.toString()), callbacks)
                binding.edtSignUpAuthNum.requestFocus()
            }else{ // 가입 이력이 있는 유저 : 로그인 유도
                this.createListenerDialog(supportFragmentManager, "goToSignIn",
                    {
                        val signInIntent  = Intent(this, SignInActivity::class.java)
                        signInIntent.putExtra("phoneNum", binding.edtSignUpPhone.text.toString())
                        startActivity(signInIntent)
                        finish()
                    },
                    null
                )
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    toast("인증 성공")
                    startActivityWithFinish(UserInfoActivity::class.java)
                } else {
                    // 인증 번호 틀린 경우
                    Log.w("auth number wrong : ", task.exception?.message.toString())
                    signUpViewModel.setAuthFail()
                }
            }
    }

    // 인증요청 버튼
    fun postSignUpAuthRequest(view: View) {
        signUpViewModel.checkValidUser(getPhoneNumber(binding.edtSignUpPhone.text.toString()))

    }

    // 인증재요청 버튼
    fun postSignUpAuthResend(view: View){
        resendAuthCode(this, getPhoneNumber(binding.edtSignUpPhone.text.toString()), resendToken, callbacks)
        binding.edtSignUpAuthNum.requestFocus()
    }

    // 회원가입 버튼
    fun checkSignUpAuth(view: View) {
        val credential = PhoneAuthProvider.getCredential(
            verificationId,
            binding.edtSignUpAuthNum.text.toString()
        )
        signInWithPhoneAuthCredential(credential)
    }

    fun goToBackListener(view: View) {
        finish()
    }
}
