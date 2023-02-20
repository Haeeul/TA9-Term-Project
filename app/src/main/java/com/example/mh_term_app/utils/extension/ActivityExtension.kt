package com.example.mh_term_app.utils.extension

import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData

fun AppCompatActivity.startActivityWithFinish(activity: Class<*>) {
    startActivity(Intent(this, activity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    this.finish()
}

fun AppCompatActivity.clearStartActivity(activity: Class<*>) {
    startActivity(Intent(this, activity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
}

fun AppCompatActivity.startActivityWithAffinity(activity: Class<*>) {
    startActivity(Intent(this, activity))
    this.finishAffinity()
}

fun AppCompatActivity.setKeyboard(show: Boolean, editText: EditText?){
    val view = this.currentFocus

    if(view != null)
    {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        when(show){
            true -> inputMethodManager.showSoftInput(editText, 0)
            false -> inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

fun AppCompatActivity.setKeyboardObserver(isValid: LiveData<Boolean>, show : Boolean){
    isValid.observe(this) {
        if(it){
            this.setKeyboard(show, null)
        }
    }
}