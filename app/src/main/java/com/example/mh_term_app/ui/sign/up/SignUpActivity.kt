package com.example.mh_term_app.ui.sign.up

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignUpBinding
import com.example.mh_term_app.ui.sign.SignViewModel
import com.example.mh_term_app.utils.extension.toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override val layoutResID: Int = R.layout.activity_sign_up
    private val signUpViewModel: SignViewModel by viewModels()

    private val auth = Firebase.auth
    private var verificationId = ""
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    private val callbacks by lazy {
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            // 번호 인증 완료 상태
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("auth Completed : ", "인증 완료")
            }

            // 번호 인증 실패 상태
            override fun onVerificationFailed(e: FirebaseException) {
                Log.w("auth Fail : ", "onVerificationFailed", e)
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
            }

            // 인증 코드 입력 대기 상태
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("auth request : ", "$verificationId / $token")
                this@SignUpActivity.verificationId = verificationId
                resendToken = token
                signUpViewModel.stopAuthTimer()
                signUpViewModel.startAuthTimer()
                toast(MHApplication.getApplicationContext().getString(R.string.txt_request_message))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            vm = signUpViewModel
            edtSignUpPhone.requestFocus()
        }
    }

    // 인증 요청
    private fun requestPhoneAuth(phoneNumber : String) {
        val optionsCompat = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
        auth.setLanguageCode("kr")
    }

    // 인증 재요청
    private fun resendAuthCode(phoneNumber: String, token: PhoneAuthProvider.ForceResendingToken?) {
        val optionsCompat = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
        if (token != null) {
            optionsCompat.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsCompat.build())
        auth.setLanguageCode("kr")
    }

    private fun getPhoneNumber(num: String): String {
        return "+82" +num.substring(1)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    toast("인증 성공")
                } else {
                    // 인증 번호 틀린 경우
                    Log.w("auth number wrong : ", task.exception?.message.toString())
                    signUpViewModel.setAuthFail()
                }
            }
    }

    // 인증요청 버튼
    fun postSignUpAuthRequest(view: View) {
        requestPhoneAuth(getPhoneNumber(viewDataBinding.edtSignUpPhone.text.toString()))
        viewDataBinding.edtSignUpAuthNum.requestFocus()
    }

    // 인증재요청 버튼
    fun postSignUpAuthResend(view: View){
        resendAuthCode(getPhoneNumber(viewDataBinding.edtSignUpPhone.text.toString()), resendToken)
        viewDataBinding.edtSignUpAuthNum.requestFocus()
    }

    // 회원가입 버튼
    fun checkSignUpAuth(view: View) {
        val credential = PhoneAuthProvider.getCredential(
            verificationId,
            viewDataBinding.edtSignUpAuthNum.text.toString()
        )
        signInWithPhoneAuthCredential(credential)
    }

    fun goToBackListener(view: View) {
        finish()
    }
}
