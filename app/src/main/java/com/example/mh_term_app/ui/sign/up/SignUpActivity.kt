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
    var verificationId = ""

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
                Log.d("auth completed : ", "인증 완료")
            }
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("auth failed : ",e.message.toString())
            }
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                this@SignUpActivity.verificationId = verificationId
                toast(MHApplication.getApplicationContext().getString(R.string.txt_request_message))
            }
        }

        val optionsCompat =  PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(getPhoneNumber(viewDataBinding.edtSignUpPhone.text.toString()))
            .setTimeout(120L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
        auth.setLanguageCode("kr")
    }

    private fun getPhoneNumber(num : String) : String{
        val firstNumber : String = num.substring(0,3)
        var phoneEdit = num.substring(3)

        when(firstNumber){
            "010" -> phoneEdit = "+8210$phoneEdit"
            "011" -> phoneEdit = "+8211$phoneEdit"
            "016" -> phoneEdit = "+8216$phoneEdit"
            "017" -> phoneEdit = "+8217$phoneEdit"
            "018" -> phoneEdit = "+8218$phoneEdit"
            "019" -> phoneEdit = "+8219$phoneEdit"
            "106" -> phoneEdit = "+82106$phoneEdit"
        }

        return phoneEdit
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                }
                else{
                    // 인증 번호 틀린 경우
                    signUpViewModel.setAuthFail()
                    Log.d("auth number wrong : ",task.exception?.message.toString())
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