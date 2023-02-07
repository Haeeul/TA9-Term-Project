package com.example.mh_term_app.ui.sign.up

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignUpBinding
import com.example.mh_term_app.ui.sign.SignViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override val layoutResID: Int = R.layout.activity_sign_up
    private val signUpViewModel : SignViewModel by viewModels()

    val auth = Firebase.auth
    val user = Firebase.auth.currentUser
    var verificationId = ""
    var phoneNum = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            vm = signUpViewModel
            edtSignUpPhone.requestFocus()
        }
    }

    private fun requestPhoneAuth(){
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("명1", "완료")
            }
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("명2",e.message.toString())
            }
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                this@SignUpActivity.verificationId = verificationId
            }
        }

        val optionsCompat =  PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+821012341234")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
        auth.setLanguageCode("kr")
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("명3","성공")
                }
                else{
                    // 인증 번호 틀린 경우
                    signUpViewModel.setAuthFail()
                    Log.d("명4",task.exception?.message.toString())
                }
            }
    }

    // 인증요청 버튼
    fun postSignUpAuthRequest(view: View){
        requestPhoneAuth()
        signUpViewModel.startAuthTimer()
        viewDataBinding.edtSignUpAuthNum.requestFocus()
    }

    // 회원가입 버튼
    fun checkSignUpAuth(view: View){
        val credential = PhoneAuthProvider.getCredential(verificationId, viewDataBinding.edtSignUpAuthNum.text.toString())
        signInWithPhoneAuthCredential(credential)
    }

    fun goToBackListener(view : View){
        finish()
    }
}