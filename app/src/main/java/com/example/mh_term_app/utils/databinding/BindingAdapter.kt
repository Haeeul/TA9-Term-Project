package com.example.mh_term_app.utils.databinding

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.google.android.material.chip.Chip

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
    @BindingAdapter("setIdNotice")
    fun TextView.setIdNotice(isValid: Boolean) {
        if(isValid){
            text = MHApplication.getApplicationContext().getString(R.string.notice_nickname_possible)
            setTextColor(ContextCompat.getColor(this.context, R.color.dark_green))
        }else{
            text = MHApplication.getApplicationContext().getString(R.string.notice_nickname_impossible)
            setTextColor(ContextCompat.getColor(this.context, R.color.red))
        }
    }

    @JvmStatic
    @BindingAdapter("setNicknameNotice")
    fun TextView.setNicknameNotice(isValid: Boolean) {
        if(isValid){
            text = MHApplication.getApplicationContext().getString(R.string.notice_nickname_possible)
            setTextColor(ContextCompat.getColor(this.context, R.color.dark_green))
        }else{
            text = MHApplication.getApplicationContext().getString(R.string.notice_nickname_impossible)
            setTextColor(ContextCompat.getColor(this.context, R.color.red))
        }
    }

    @JvmStatic
    @BindingAdapter("setValidSignInfoIcon")
    fun ImageView.setValidSignInfoIcon(isValid: Boolean) {
        if(isValid){
            setImageResource(R.drawable.ic_nick_possible)
        }else{
            setImageResource(R.drawable.ic_nick_impossible)
        }
    }

    @JvmStatic
    @BindingAdapter("setReviewIcon")
    fun ImageView.setReviewIcon(isLike: Boolean) {
        if(isLike){
            setImageResource(R.drawable.ic_heart)
        }else{
            setImageResource(R.drawable.ic_heart_empty)
        }
    }

    @SuppressLint("ResourceType")
    @JvmStatic
    @BindingAdapter("setUserTypeChip")
    fun Chip.setUserTypeChip(type: String) {
        text = type

//        when(type){
//            "휠체어 사용자" -> this.setChipBackgroundColorResource(ContextCompat.getColor(this.context, R.color.red))
//            "영유아 보호자" -> this.setChipBackgroundColorResource(ContextCompat.getColor(this.context, R.color.pink))
//            "시각 장애인" -> this.setChipBackgroundColorResource(ContextCompat.getColor(this.context, R.color.yellow))
//            "목발 사용 등 부상자" -> this.setChipBackgroundColorResource(ContextCompat.getColor(this.context, R.color.purple))
//            "노약자" -> this.setChipBackgroundColorResource(ContextCompat.getColor(this.context, R.color.beige))
//            else -> this.setChipBackgroundColorResource(ContextCompat.getColor(this.context, R.color.black))
//        }
    }
}