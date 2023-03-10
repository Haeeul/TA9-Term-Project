package com.example.mh_term_app.utils.extension

import android.content.Context
import android.widget.Toast
import com.example.mh_term_app.R

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.errorToast() {
    Toast.makeText(this, getString(R.string.txt_timeout), Toast.LENGTH_SHORT).show()
}