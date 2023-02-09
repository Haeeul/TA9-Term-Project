package com.example.mh_term_app.utils.databinding

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("setAuthNotice")
    fun TextView.setAuthNotice(type: Int) {
        when (type) {
            1 -> {
                this.text = MHApplication.getApplicationContext().getString(R.string.txt_auth_timeout)
            }
            2 -> {
                this.text = MHApplication.getApplicationContext().getString(R.string.txt_auth_fail)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setNicknameNotice")
    fun TextView.setNicknameNotice(isValid: Boolean) {
        if(isValid){
            text = MHApplication.getApplicationContext().getString(R.string.txt_nickname_possible)
            setTextColor(ContextCompat.getColor(this.context, R.color.dark_green))
        }else{
            text = MHApplication.getApplicationContext().getString(R.string.txt_nickname_impossible)
            setTextColor(ContextCompat.getColor(this.context, R.color.red))
        }
    }

    @JvmStatic
    @BindingAdapter("setNicknameIcon")
    fun ImageView.setNicknameIcon(isValid: Boolean) {
        if(isValid){
            setImageResource(R.drawable.ic_nick_possible)
        }else{
            setImageResource(R.drawable.ic_nick_impossible)
        }
    }
}