package com.example.mh_term_app.ui.menu.report

import android.view.View
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityReportPlaceBinding
import com.example.mh_term_app.utils.extension.clearStartActivity
import com.example.mh_term_app.utils.extension.setSingleOnClickListener

class ReportPlaceActivity : BaseActivity<ActivityReportPlaceBinding>(){
    override val layoutResID: Int
        get() = R.layout.activity_report_place

    override fun initView() {
        super.initView()

        binding.tbReportPlace.apply {
            title = MHApplication.getApplicationContext().getString(R.string.title_menu_report)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }
    }

    fun goToReportType(view : View){
        this.clearStartActivity(ReportTypeActivity::class.java)
    }
}