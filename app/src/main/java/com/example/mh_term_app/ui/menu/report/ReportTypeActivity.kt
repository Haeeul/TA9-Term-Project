package com.example.mh_term_app.ui.menu.report

import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityReportTypeBinding
import com.example.mh_term_app.utils.extension.setSingleOnClickListener

class ReportTypeActivity : BaseActivity<ActivityReportTypeBinding>(){
    override val layoutResID: Int
        get() = R.layout.activity_report_type

    override fun initView() {
        super.initView()

        binding.tbReportType.apply {
            title = MHApplication.getApplicationContext().getString(R.string.title_menu_report)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }
    }
}