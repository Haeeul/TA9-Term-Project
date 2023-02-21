package com.example.mh_term_app.ui.menu.report

import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityReportInfoStoreBinding
import com.example.mh_term_app.utils.extension.setSingleOnClickListener

class ReportInfoStoreActivity : BaseActivity<ActivityReportInfoStoreBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_report_info_store

    override fun initView() {
        super.initView()

        binding.tbReportInfoStore.apply {
            title = MHApplication.getApplicationContext().getString(R.string.title_menu_report)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }
    }
}