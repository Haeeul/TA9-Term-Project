package com.example.mh_term_app.utils.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mh_term_app.R
import com.example.mh_term_app.databinding.DialogMoveBinding
import com.example.mh_term_app.utils.extension.setSingleOnClickListener

class MoveDialog : DialogFragment(), View.OnClickListener {
    lateinit var binding : DialogMoveBinding
    var listener: CustomDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogMoveBinding.inflate(inflater, container, false)
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

    private fun processBundle(binding: DialogMoveBinding) {
        val bundle = arguments
        when (bundle?.getString("type", "")) {
            "goToSignIn" -> {
                setContents(R.string.desc_go_to_sign_in, "이동")
            }
            "goToSignUp" -> {
                setContents(R.string.desc_go_to_sign_up, "이동")
            }
            "update" -> {
                setContents(R.string.desc_update_back, "이동")
            }
            "login" -> {
                setContents(R.string.desc_need_login, "이동")
            }
        }

        binding.btnMoveDialogConfirm.setSingleOnClickListener {
            dismiss()
            listener?.onPositiveClicked()
        }

        binding.btnMoveDialogCancel.setSingleOnClickListener {
            dismiss()
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    private fun setContents(title: Int, confirm: String){
        binding.txtMoveDialogContent.text = getString(title)
        binding.btnMoveDialogConfirm.text = confirm
    }

    inner class CustomDialogBuilder {
        private val dialog = MoveDialog()

        fun setBtnClickListener(listener: CustomDialogListener): CustomDialogBuilder {
            dialog.listener = listener
            return this
        }

        fun getInstance(): MoveDialog {
            return dialog
        }
    }

    interface CustomDialogListener{
        fun onPositiveClicked()
        fun onNegativeClicked()
    }

}