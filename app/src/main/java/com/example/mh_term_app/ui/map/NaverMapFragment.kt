package com.example.mh_term_app.ui.map

import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.databinding.FragmentNaverMapBinding

class NaverMapFragment : BaseFragment<FragmentNaverMapBinding>() {
    override val layoutResID
        get() = R.layout.fragment_naver_map

    override fun initListener() {
        super.initListener()

        binding.edtMapSearch.setOnClickListener {
            var  activity = activity as MainActivity
            activity.goToSearchListener()
        }
    }
}