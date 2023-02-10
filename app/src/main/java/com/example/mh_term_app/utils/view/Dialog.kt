package com.example.mh_term_app.utils.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.mh_term_app.R

fun DialogFragment.setDrawable(){
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog?.window?.requestFeature(
        Window.FEATURE_NO_TITLE
    )
}

fun DialogFragment.setHeight(){
    val width = resources.getDimensionPixelSize(R.dimen.dialog_width)

    dialog?.window?.setBackgroundDrawableResource(R.drawable.background_dialog_sign_guide)
    dialog?.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
}