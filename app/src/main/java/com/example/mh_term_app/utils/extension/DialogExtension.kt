package com.example.mh_term_app.utils.extension

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.mh_term_app.ui.menu.report.ReportViewModel
import com.example.mh_term_app.utils.view.DialogViewModel
import com.example.mh_term_app.utils.view.MoveDialog
import com.example.mh_term_app.utils.view.StoreTimeDialog
import com.example.mh_term_app.utils.view.TimePickerDialog


fun Context.createDialog(fragmentManager: FragmentManager, type: String) {
    val bundle = Bundle()
    bundle.putString("type", type)
    val dialog: MoveDialog = MoveDialog().CustomDialogBuilder().getInstance()
    dialog.arguments = bundle
    dialog.show(fragmentManager, dialog.tag)
}

fun Context.createGoToDialog(fragmentManager: FragmentManager, type: String, positiveClicked:(() -> Unit)?, negativeClicked: (() -> Unit)?) {
    val bundle = Bundle()
    bundle.putString("type", type)
    val dialog: MoveDialog = MoveDialog().CustomDialogBuilder()
        .setBtnClickListener(object : MoveDialog.CustomDialogListener {
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

fun Context.createStoreTimeDialog(fragmentManager: FragmentManager, type: String, vm : ReportViewModel, positiveClicked:(() -> Unit)?, negativeClicked: (() -> Unit)?) {
//    val bundle = Bundle()
//    bundle.putString("type", type)
    val dialog: StoreTimeDialog = StoreTimeDialog(type, vm).CustomDialogBuilder()
        .setBtnClickListener(object : StoreTimeDialog.CustomDialogListener {
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
//    dialog.arguments = bundle
    dialog.show(fragmentManager, dialog.tag)
}

fun Context.createStoreTimeDialog(fragmentManager: FragmentManager, type: String, vm : ReportViewModel) {
    val dialog: StoreTimeDialog = StoreTimeDialog(type, vm).CustomDialogBuilder().getInstance()
    dialog.show(fragmentManager, dialog.tag)
}

fun Context.createTimePickerDialog(fragmentManager: FragmentManager, type: String, vm : DialogViewModel) {
    val dialog: TimePickerDialog = TimePickerDialog(type, vm).CustomDialogBuilder().getInstance()
    dialog.show(fragmentManager, dialog.tag)
}