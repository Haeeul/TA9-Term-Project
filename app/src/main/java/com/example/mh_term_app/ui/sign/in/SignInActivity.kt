package com.example.mh_term_app.ui.sign.`in`

import androidx.activity.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySignInBinding
import com.example.mh_term_app.ui.sign.up.SignUpViewModel
import com.google.firebase.auth.PhoneAuthProvider

class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_sign_in
    private val signInViewModel : SignUpViewModel by viewModels()

    private var verificationId = ""
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

//    private val callbacks by lazy {
//        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            // 번호 인증 완료 상태
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                Log.d("auth Completed : ", "인증 완료")
//            }
//
//            // 번호 인증 실패 상태
//            override fun onVerificationFailed(e: FirebaseException) {
//                Log.w("auth Fail : ", "onVerificationFailed", e)
//                if (e is FirebaseAuthInvalidCredentialsException) {
//                    // Invalid request
//                } else if (e is FirebaseTooManyRequestsException) {
//                    // The SMS quota for the project has been exceeded
//                }
//            }
//
//            // 인증 코드 입력 대기 상태
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//                Log.d("auth request : ", "$verificationId / $token")
//                this@SignInActivity.verificationId = verificationId
//                resendToken = token
//                signInViewModel.stopAuthTimer()
//                signInViewModel.startAuthTimer()
//                toast(MHApplication.getApplicationContext().getString(R.string.txt_request_message))
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding.apply {
//            vm = signInViewModel
//            edtSignInPhone.requestFocus()
//        }
//
//        initObserver()
//    }
//
//    private fun initObserver(){
//        signInViewModel.isValidEmail.observe(this){
//            if(it){ // 가입 이력이 없는 유저 : 회원가입 유도
//                this.createListenerDialog(supportFragmentManager, "goToSignUp",
//                    {
//                        startActivityWithFinish(SignUpActivity::class.java)
//                    },
//                    null
//                )
//            }else{ // 가입 이력이 있는 유저 : 인증 요청 진행
//                FirebaseAuth.requestPhoneAuth(
//                    this,
//                    getPhoneNumber(binding.edtSignInPhone.text.toString()),
//                    callbacks
//                )
//                binding.edtSighInAuthNum.requestFocus()
//            }
//        }
//    }
//
//
//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    toast("인증 성공")
//                    startActivityWithAffinity(MainActivity::class.java)
//                } else {
//                    // 인증 번호 틀린 경우
//                    Log.w("auth number wrong : ", task.exception?.message.toString())
//                    signInViewModel.setAuthFail()
//                }
//            }
//    }
//
//    // 인증요청 버튼
//    fun postSignInAuthRequest(view: View){
//        signInViewModel.checkValidUser(getPhoneNumber(binding.edtSignInPhone.text.toString()))
//    }
//
//    // 인증재요청 버튼
//    fun postSignIpAuthResend(view: View){
//        resendAuthCode(this,getPhoneNumber(binding.edtSignInPhone.text.toString()),resendToken, callbacks)
//        binding.edtSighInAuthNum.requestFocus()
//    }
//
//    // 로그인 버튼
//    fun checkSignInAuth(view: View){
//        val credential = PhoneAuthProvider.getCredential(
//            verificationId,
//            binding.edtSighInAuthNum.text.toString()
//        )
//        signInWithPhoneAuthCredential(credential)
//    }
//
//    fun goToBackListener(view : View){
//        finish()
//    }
//
//    fun goToSignUpListener(view : View){
//        startActivityWithFinish(SignUpActivity::class.java)
//    }
}