package com.example.mh_term_app.ui.map

import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.databinding.FragmentNaverMapBinding
import com.example.mh_term_app.utils.extension.toast

class NaverMapFragment : BaseFragment<FragmentNaverMapBinding>(){
    override val layoutResID
        get() = R.layout.fragment_naver_map

    override fun initListener() {
        super.initListener()

        binding.edtMapSearch.setOnClickListener {
            val activity = activity as MainActivity
            activity.goToSearchListener()
            activity.setInfoWindowVisibility(false)
        }

        binding.chipFacility.setOnClickListener {
            context?.toast("장소 준비중")
        }

        binding.chipStore.setOnClickListener {
            context?.toast("장소 준비중")
        }

        binding.chipChargingStation.setOnClickListener {
            context?.toast("장소 준비중")
        }

        binding.chipRestroom.setOnClickListener {
            context?.toast("장소 준비중")
        }

        binding.chipSupportCenter.setOnClickListener {
            context?.toast("장소 준비중")
        }
    }
}