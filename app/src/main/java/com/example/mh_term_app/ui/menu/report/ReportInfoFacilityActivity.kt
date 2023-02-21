package com.example.mh_term_app.ui.menu.report

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityReportInfoFacilityBinding
import com.example.mh_term_app.utils.extension.setSingleOnClickListener

class ReportInfoFacilityActivity : BaseActivity<ActivityReportInfoFacilityBinding>(){
    override val layoutResID: Int
        get() = R.layout.activity_report_info_facility

    override fun initView() {
        super.initView()

        binding.tbReportInfoFacility.apply {
            title = MHApplication.getApplicationContext().getString(R.string.title_menu_report)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }
    }
}