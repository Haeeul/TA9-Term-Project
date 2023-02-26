package com.example.mh_term_app.utils.extension

import android.content.Intent
import androidx.fragment.app.Fragment

fun Fragment.intent(activity: Class<*>) {
    val intent = Intent(requireContext(), activity)
    startActivity(intent)
}