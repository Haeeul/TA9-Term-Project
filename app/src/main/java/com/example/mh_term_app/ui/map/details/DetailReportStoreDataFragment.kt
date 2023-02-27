package com.example.mh_term_app.ui.map.details

import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.request.RequestPlaceStore
import com.example.mh_term_app.databinding.FragmentDetailReportStoreDataBinding


class DetailReportStoreDataFragment() : BaseFragment<FragmentDetailReportStoreDataBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_detail_report_store_data

    override fun initView() {
        super.initView()


    }

    fun setData(item: RequestPlaceStore){
//        binding.item = item
    }


}