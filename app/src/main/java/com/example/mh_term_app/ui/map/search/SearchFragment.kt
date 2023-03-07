package com.example.mh_term_app.ui.map.search

import android.os.Bundle
import android.view.View
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.databinding.FragmentSearchBinding
import com.example.mh_term_app.utils.extension.setSingleOnClickListener

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            editTextTextPersonName.requestFocus()
        }
    }

    override fun initListener() {
        super.initListener()

        binding.btnSearchBack.setSingleOnClickListener {
            val activity = activity as MainActivity
            activity.setInfoWindowVisibility(false)
        }
    }

}