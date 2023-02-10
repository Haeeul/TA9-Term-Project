package com.example.mh_term_app.utils.etc

import android.app.Activity
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

object FirebaseAuth {
    val auth = Firebase.auth

    fun getPhoneNumber(num: String): String {
        return "+82" +num.substring(1)
    }

    // 인증 요청
    fun requestPhoneAuth(activity: Activity, phoneNumber : String, callbacks : OnVerificationStateChangedCallbacks) {
        val optionsCompat = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(optionsCompat)
        auth.setLanguageCode("kr")
    }

    // 인증 재요청
    fun resendAuthCode(activity: Activity, phoneNumber: String, token: PhoneAuthProvider.ForceResendingToken?, callbacks : OnVerificationStateChangedCallbacks) {
        val optionsCompat = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
        if (token != null) {
            optionsCompat.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsCompat.build())
        auth.setLanguageCode("kr")
    }
}