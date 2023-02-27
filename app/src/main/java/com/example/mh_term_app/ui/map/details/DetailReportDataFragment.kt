package com.example.mh_term_app.ui.map.details

import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.request.RequestPlaceStore
import com.example.mh_term_app.databinding.FragmentDetailReportDataBinding


class DetailReportDataFragment() : BaseFragment<FragmentDetailReportDataBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_detail_report_data

    override fun initView() {
        super.initView()


    }

    fun setData(item: RequestPlaceStore){
//        binding.item = item
    }


}