package com.example.mh_term_app.utils.extension

import android.view.View
import com.example.mh_term_app.utils.listener.SingleClickListener

fun View.setSingleOnClickListener(onSingleClick: (View) -> Unit) {
    val singleClickListener =
        SingleClickListener {
            onSingleClick(it)
        }
    setOnClickListener(singleClickListener)
}