package com.example.mh_term_app.utils.listener

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

fun EditText.setListenerToEditText(context: Context) {
    this.setOnKeyListener { view, keyCode, event ->
        // Enter Key Action
        if (event.action == KeyEvent.ACTION_DOWN
            && keyCode == KeyEvent.KEYCODE_ENTER
        ) {
            // 키패드 내리기
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.windowToken, 0)
            true
        }

        false
    }
}