package com.example.managerapplication.utils.extension

import android.view.View
import com.example.managerapplication.utils.listener.SingleClickListener

fun View.setSingleOnClickListener(onSingleClick: (View) -> Unit) {
    val singleClickListener =
        SingleClickListener {
            onSingleClick(it)
        }
    setOnClickListener(singleClickListener)
}