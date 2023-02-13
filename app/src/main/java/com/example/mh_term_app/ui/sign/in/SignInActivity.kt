package com.example.mh_term_app.ui.sign.`in`

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignInBinding
import com.example.mh_term_app.ui.map.MapActivity
import com.example.mh_term_app.ui.sign.SignViewModel
import com.example.mh_term_app.ui.sign.up.SignUpActivity
import com.example.mh_term_app.utils.etc.FirebaseAuth
import com.example.mh_term_app.utils.etc.FirebaseAuth.auth
import com.example.mh_term_app.utils.etc.FirebaseAuth.getPhoneNumber
import com.example.mh_term_app.utils.etc.FirebaseAuth.resendAuthCode
import com.example.mh_term_app.utils.extension.createListenerDialog
import com.example.mh_term_app.utils.extension.startActivityWithAffinity
import com.example.mh_term_app.utils.extension.startActivityWithFinish
import com.example.mh_term_app.utils.extension.toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    override val layoutResID: Int = R.layout.activity_sign_in
    private val signInViewModel : SignViewModel by viewModels()

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
                this@SignInActivity.verificationId = verificationId
                resendToken = token
                signInViewModel.stopAuthTimer()
                signInViewModel.startAuthTimer()
                toast(MHApplication.getApplicationContext().getString(R.string.txt_request_message))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.apply {
            vm = signInViewModel
            edtSignInPhone.requestFocus()
        }

        initObserver()
    }

    private fun initObserver(){
        signInViewModel.isValidPhone.observe(this){
            if(it){ // 가입 이력이 없는 유저 : 회원가입 유도
                this.createListenerDialog(supportFragmentManager, "goToSignUp",
                    {
                        startActivityWithFinish(SignUpActivity::class.java)
                    },
                    null
                )
            }else{ // 가입 이력이 있는 유저 : 인증 요청 진행
                FirebaseAuth.requestPhoneAuth(
                    this,
                    getPhoneNumber(viewDataBinding.edtSignInPhone.text.toString()),
                    callbacks
                )
                viewDataBinding.edtSighInAuthNum.requestFocus()
            }
        }
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    toast("인증 성공")
                    startActivityWithAffinity(MapActivity::class.java)
                } else {
                    // 인증 번호 틀린 경우
                    Log.w("auth number wrong : ", task.exception?.message.toString())
                    signInViewModel.setAuthFail()
                }
            }
    }

    // 인증요청 버튼
    fun postSignInAuthRequest(view: View){
        signInViewModel.checkValidUser(getPhoneNumber(viewDataBinding.edtSignInPhone.text.toString()))
    }

    // 인증재요청 버튼
    fun postSignIpAuthResend(view: View){
        resendAuthCode(this,getPhoneNumber(viewDataBinding.edtSignInPhone.text.toString()),resendToken, callbacks)
        viewDataBinding.edtSighInAuthNum.requestFocus()
    }

    // 로그인 버튼
    fun checkSignInAuth(view: View){
        val credential = PhoneAuthProvider.getCredential(
            verificationId,
            viewDataBinding.edtSighInAuthNum.text.toString()
        )
        signInWithPhoneAuthCredential(credential)
    }

    fun goToBackListener(view : View){
        finish()
    }

    fun goToSignUpListener(view : View){
        startActivityWithFinish(SignUpActivity::class.java)
    }
}