package com.example.mh_term_app.ui.menu.report

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityReportPlaceBinding
import com.example.mh_term_app.utils.extension.changeKeywordColor
import com.example.mh_term_app.utils.extension.clearStartActivity
import com.example.mh_term_app.utils.extension.setSingleOnClickListener


class ReportPlaceActivity : BaseActivity<ActivityReportPlaceBinding>(){
    override val layoutResID: Int
        get() = R.layout.activity_report_place
    private val reportPlaceViewModel : ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            vm = reportPlaceViewModel
            txtReportPlaceTitle.changeKeywordColor(
                MHApplication.getApplicationContext().getString(R.string.desc_report_place_start),
                MHApplication.getApplicationContext().getString(R.string.desc_report_place_end),
                10, 12, 2,4)
        }
    }

    override fun initView() {
        super.initView()

        binding.tbReportPlace.apply {
            title = MHApplication.getApplicationContext().getString(R.string.title_menu_report)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }
    }

    override fun initObserver() {
        super.initObserver()

        reportPlaceViewModel.typeTxt.observe(this){
            reportPlaceViewModel.checkValidNextBtn()
        }
    }

    override fun initListener() {
        super.initListener()

        getPlaceType()
    }

    private fun getPlaceType() {
        binding.rgReportPlaceType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_report_type_store -> reportPlaceViewModel.setTypeTxt(getString(R.string.txt_place_store))
                R.id.rb_report_type_facility -> reportPlaceViewModel.setTypeTxt(getString(R.string.txt_place_facility))
            }
        }
    }

    fun goToReportType(view : View){
        if(binding.rbReportTypeStore.isChecked) this.clearStartActivity(ReportInfoStoreActivity::class.java)
        else this.clearStartActivity(ReportInfoFacilityActivity::class.java)
    }
}