package com.example.mh_term_app.utils.databinding

import android.widget.TextView
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
}