package com.example.mh_term_app.utils.extension

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.mh_term_app.utils.view.SignGuideDialog


fun Context.createDialog(fragmentManager: FragmentManager, title: String) {
    val bundle = Bundle()
    bundle.putString("title", title)
    val dialog: SignGuideDialog = SignGuideDialog().CustomDialogBuilder().getInstance()
    dialog.arguments = bundle
    dialog.show(fragmentManager, dialog.tag)
}

fun Context.createListenerDialog(fragmentManager: FragmentManager, title: String, positiveClicked:(() -> Unit)?, negativeClicked: (() -> Unit)?) {
    val bundle = Bundle()
    bundle.putString("title", title)
    val dialog: SignGuideDialog = SignGuideDialog().CustomDialogBuilder()
        .setBtnClickListener(object : SignGuideDialog.CustomDialogListener {
            override fun onPositiveClicked() {
                if (positiveClicked != null) {
                    positiveClicked()
                }
            }
            override fun onNegativeClicked() {
                if (negativeClicked != null) {
                    negativeClicked()
                }
            }
        })
        .getInstance()
    dialog.arguments = bundle
    dialog.show(fragmentManager, dialog.tag)
}