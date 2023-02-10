package com.example.mh_term_app.utils.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mh_term_app.R
import com.example.mh_term_app.databinding.DialogSignGuideBinding

class SignGuideDialog : DialogFragment(), View.OnClickListener {
    lateinit var binding : DialogSignGuideBinding
    var listener: CustomDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSignGuideBinding.inflate(inflater, container, false)
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

    private fun processBundle(binding: DialogSignGuideBinding) {
        val bundle = arguments
        when (bundle?.getString("type", "")) {
            "goToSignIn" -> {
                setContents(R.string.txt_go_to_sign_in, "이동")
            }
            "goToSignUp" -> {
                setContents(R.string.txt_go_to_sign_up, "이동")
            }
        }

        binding.btnSignGuideConfirm.setOnClickListener {
            dismiss()
            listener?.onPositiveClicked()
        }

        binding.btnSignGuideCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    private fun setContents(title: Int, confirm: String){
        binding.txtSignGuideContent.text = getString(title)
        binding.btnSignGuideConfirm.text = confirm
    }

    inner class CustomDialogBuilder {
        private val dialog = SignGuideDialog()

        fun setBtnClickListener(listener: CustomDialogListener): CustomDialogBuilder {
            dialog.listener = listener
            return this
        }

        fun getInstance(): SignGuideDialog {
            return dialog
        }
    }

    interface CustomDialogListener{
        fun onPositiveClicked()
        fun onNegativeClicked()
    }

}