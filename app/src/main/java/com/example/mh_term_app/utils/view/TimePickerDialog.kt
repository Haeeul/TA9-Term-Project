package com.example.mh_term_app.utils.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mh_term_app.databinding.DialogTimePickerBinding
import com.example.mh_term_app.utils.extension.setSingleOnClickListener


class TimePickerDialog(val type : String, val vm : DialogViewModel) : DialogFragment(), View.OnClickListener {
    lateinit var binding : DialogTimePickerBinding
    var listener: CustomDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTimePickerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        this.setDrawable()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        this.setHeight()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        processBundle(binding)
    }

    private fun processBundle(binding: DialogTimePickerBinding) {
        val bundle = arguments

        binding.btnTimePickerComplete.setSingleOnClickListener {
            dismiss()
            getTime(type)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getTime(type: String){
        when(type){
            "open" -> {
                vm.openHourTxt.value = binding.tpReport.hour.toString()
                vm.openMinuteTxt.value = binding.tpReport.minute.toString()
                vm.setTimePickerValue(type)
            }
            "close" -> {
                vm.closeHourTxt.value = binding.tpReport.hour.toString()
                vm.closeMinuteTxt.value = binding.tpReport.minute.toString()
                vm.setTimePickerValue(type)
            }
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    inner class CustomDialogBuilder {
        private val dialog = TimePickerDialog(type, vm)

        fun setBtnClickListener(listener: CustomDialogListener): CustomDialogBuilder {
            dialog.listener = listener
            return this
        }

        fun getInstance(): TimePickerDialog {
            return dialog
        }
    }

    interface CustomDialogListener{
        fun onPositiveClicked()
        fun onNegativeClicked()
    }

}