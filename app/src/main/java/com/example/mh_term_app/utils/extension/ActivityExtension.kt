package com.example.mh_term_app.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.clearStartActivity(activity: Class<*>) {
    startActivity(Intent(this, activity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
}

fun AppCompatActivity.startActivityWithFinish(activity: Class<*>) {
    startActivity(Intent(this, activity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    this.finishAffinity()
}