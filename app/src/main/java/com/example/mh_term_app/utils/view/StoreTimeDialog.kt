package com.example.mh_term_app.utils.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.data.model.Time
import com.example.mh_term_app.databinding.DialogStoreTimeBinding
import com.example.mh_term_app.ui.menu.report.ReportViewModel
import com.example.mh_term_app.utils.extension.createTimePickerDialog
import com.example.mh_term_app.utils.extension.setSingleOnClickListener

class StoreTimeDialog(val type: String, val vm: ReportViewModel) : DialogFragment(),
    View.OnClickListener {
    lateinit var binding: DialogStoreTimeBinding
    var listener: CustomDialogListener? = null
    private val dialogViewModel: DialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogStoreTimeBinding.inflate(inflater, container, false)
        binding.apply {
            vm = dialogViewModel
            lifecycleOwner = viewLifecycleOwner
        }

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
        initObserver()
    }

    @SuppressLint("SetTextI18n")
    private fun processBundle(binding: DialogStoreTimeBinding) {

        binding.rgTimeDay.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_store_open -> {
                    dialogViewModel.setStoreTime("open")
                    binding.cbStoreAllTime.isEnabled = true
                }
                R.id.rb_store_close -> {
                    dialogViewModel.setStoreTime("close")
                    binding.cbStoreAllTime.isEnabled = false
                }
            }
        }

        binding.cbStoreAllTime.setSingleOnClickListener {
            dialogViewModel.setAllTime(binding.cbStoreAllTime.isChecked)
        }

        binding.txtStoreTimeStart.setSingleOnClickListener {
            context?.createTimePickerDialog(parentFragmentManager, "open", dialogViewModel)
        }

        binding.txtStoreTimeEnd.setSingleOnClickListener {
            context?.createTimePickerDialog(parentFragmentManager, "close", dialogViewModel)
        }

        binding.btnStoreTimeComplete.setSingleOnClickListener {
            setTimeValue(type)
            dismiss()
        }

        binding.btnStoreTimeClose.setSingleOnClickListener {
            dismiss()
        }
    }

    private fun setTimeValue(type: String) {
        if(binding.rbStoreClose.isChecked) dialogViewModel.setAutoTime("-1","-1","-1","-1")
        else if(binding.cbStoreAllTime.isChecked) dialogViewModel.setAutoTime("0","0","23","59")

        val storeTime = Time(
            dialogViewModel.openHourTxt.value.toString(),
            dialogViewModel.openMinuteTxt.value.toString(),
            dialogViewModel.closeHourTxt.value.toString(),
            dialogViewModel.closeMinuteTxt.value.toString()
        )

        vm.setTimeValue(type, storeTime)
    }

    private fun initObserver(){
        dialogViewModel.openTimeTxt.observe(this) {
            dialogViewModel.checkCompleteBtn()
        }
        dialogViewModel.closeTimeTxt.observe(this){
            dialogViewModel.checkCompleteBtn()
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    inner class CustomDialogBuilder {
        private val dialog = StoreTimeDialog(type, vm)

        fun setBtnClickListener(listener: CustomDialogListener): CustomDialogBuilder {
            dialog.listener = listener
            return this
        }

        fun getInstance(): StoreTimeDialog {
            return dialog
        }
    }

    interface CustomDialogListener {
        fun onPositiveClicked()
        fun onNegativeClicked()
    }
}