package com.example.mh_term_app.utils.databinding

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
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
            text = MHApplication.getApplicationContext().getString(R.string.notice_id_possible)
            setTextColor(ContextCompat.getColor(this.context, R.color.dark_green))
        }else{
            text = MHApplication.getApplicationContext().getString(R.string.notice_id_impossible)
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

        var color = R.color.light_red

        when(type){
            "휠체어 사용자" -> color = R.color.green
            "영유아 보호자" -> color = R.color.pink
            "시각 장애인" -> color = R.color.yellow
            "목발 사용 등 부상자" -> color = R.color.purple
            "노약자" -> color = R.color.back_light_grey
            "none" -> this.visibility = View.GONE
        }

        setChipBackgroundColorResource(color)
    }

    @JvmStatic
    @BindingAdapter("setStoreTime")
    fun TextView.setStoreTime(isEnabled: Boolean) {
        this.isEnabled = isEnabled
        if(isEnabled){
            setBackgroundResource(R.drawable.round_square_line_grey_8)
            setTextColor(ContextCompat.getColor(this.context, R.color.light_black))
        }else{
            setBackgroundResource(R.drawable.round_square_line_fill_grey_8)
            setTextColor(ContextCompat.getColor(this.context, R.color.dark_grey))
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("setPhoneFormat")
    fun TextView.setPhoneFormat(phone : String) {

        TODO()
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "setReviewContentsText", event = "TextAttrChanged")
    fun getNoteContentsSizeText(view: EditText): String {
        return view.text.toString()
    }

    @JvmStatic
    @BindingAdapter("setReviewContentsText")
    fun setReviewContentsText(view: EditText, text: String) {
        when (text.length) {
            0 -> {
                view.setBackgroundResource(R.drawable.round_square_line_fill_grey_8)
            }
            else -> {
                view.setBackgroundResource(R.drawable.round_square_line_grey_8)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("TextAttrChanged")
    fun setTextAttrChanged(view: EditText, listener: InverseBindingListener) {
        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listener.onChange()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    @JvmStatic
    @BindingAdapter("setCallIcon")
    fun ImageView.setCallIcon(phone: String) {
        if(phone == "none" || phone == "null") this.visibility = View.GONE
        else this.visibility = View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("setCallTxt")
    fun TextView.setCallTxt(phone: String) {
        if(phone == "none"|| phone == "null") this.visibility = View.GONE
        else this.visibility = View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("setBooleanTxt")
    fun TextView.setBooleanTxt(boolean: String) {
        if(boolean == "Y"){
            text = MHApplication.getApplicationContext().getString(R.string.txt_possible)
            setTextColor(ContextCompat.getColor(this.context, R.color.dark_green))
        }else{
            text = MHApplication.getApplicationContext().getString(R.string.txt_impossible)
            setTextColor(ContextCompat.getColor(this.context, R.color.red))
        }
    }
}