package com.example.mh_term_app.ui.menu.report

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityReportInfoFacilityBinding
import com.example.mh_term_app.utils.extension.*


class ReportInfoFacilityActivity : BaseActivity<ActivityReportInfoFacilityBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_report_info_facility
    private val reportPlaceViewModel: ReportViewModel by viewModels()

    private var facilityType = ""
    private var facilityAddress = ""
    private var facilityLatitude = 0.0
    private var facilityLongitude = 0.0

    private var checkedGroup : Int = 0
    private var checkedId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        facilityType = intent.getStringExtra("type").toString()
        facilityAddress = intent.getStringExtra("address").toString()
        facilityLatitude = intent.getDoubleExtra("latitude",0.0)
        facilityLongitude = intent.getDoubleExtra("longitude",0.0)

        binding.apply {
            vm = reportPlaceViewModel
            txtReportInfoFacilityTitle.changeKeywordColor(
                MHApplication.getApplicationContext()
                    .getString(R.string.desc_report_type_facility_start),
                MHApplication.getApplicationContext()
                    .getString(R.string.desc_report_type_facility_end),
                7, 10, 2, 5
            )
        }
    }

    override fun initView() {
        super.initView()

        binding.tbReportInfoFacility.apply {
            title = MHApplication.getApplicationContext()
                .getString(R.string.title_menu_report)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }
    }

    override fun initObserver() {
        super.initObserver()

        setFacilityObserver(reportPlaceViewModel.locationTxt)
        setFacilityObserver(reportPlaceViewModel.detailTypeTxt)
        setFacilityObserver(reportPlaceViewModel.plusInfoTxt)

        reportPlaceViewModel.isValidPost.observe(this) {
            if (it) toast("제보 완료")
            else errorToast()
            startActivityWithAffinity(MainActivity::class.java)
        }
    }

    private fun setFacilityObserver(data: LiveData<String>) {
        data.observe(this) {
            reportPlaceViewModel.checkValidFacilityCompleteBtn()
        }
    }

    override fun initListener() {
        super.initListener()

        getFacilityDetailLocation()
        getFacilityDetailType()
    }

    private fun getFacilityDetailLocation() {
        binding.rgLocationFacility.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_facility_inside -> reportPlaceViewModel.locationTxt.value = (getString(R.string.txt_inside))
                R.id.rb_facility_outside -> reportPlaceViewModel.locationTxt.value = (getString(R.string.txt_outside))
            }
        }
    }

    private fun getFacilityDetailType() {
        binding.rgFacilityType1.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId != -1 && checkedGroup != group.id){
                binding.rgFacilityType2.clearCheck()
                binding.rgFacilityType3.clearCheck()
                this.checkedId = checkedId
                checkedGroup = group.id
            }

            when (checkedId) {
                R.id.rb_facility_bollard -> {
                    reportPlaceViewModel.setDetailTypeTxt(getString(R.string.txt_bollard))
                }
                R.id.rb_facility_street -> {
                    reportPlaceViewModel.setDetailTypeTxt(getString(R.string.txt_street))
                }
                R.id.rb_facility_block -> {
                    reportPlaceViewModel.setDetailTypeTxt(getString(R.string.txt_block))
                }
            }
        }

        binding.rgFacilityType2.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId != -1 && checkedGroup != group.id){
                binding.rgFacilityType1.clearCheck()
                binding.rgFacilityType3.clearCheck()
                this.checkedId = checkedId
                checkedGroup = group.id
            }
            when (checkedId) {
                R.id.rb_facility_car_area -> {
                    reportPlaceViewModel.setDetailTypeTxt(getString(R.string.txt_car_area))
                }
                R.id.rb_facility_crosswalk -> {
                    reportPlaceViewModel.setDetailTypeTxt(getString(R.string.txt_crosswalk))
                }
            }
        }

        binding.rgFacilityType3.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId != -1&& checkedGroup != group.id){
                binding.rgFacilityType1.clearCheck()
                binding.rgFacilityType2.clearCheck()
                this.checkedId = checkedId
                checkedGroup = group.id
            }
            when (checkedId) {
                R.id.rb_facility_pass -> {
                    reportPlaceViewModel.setDetailTypeTxt(getString(R.string.txt_pass))
                }
            }
        }
    }

    fun onFacilityTargetClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            reportPlaceViewModel.clickTargetBtn(checked, view.text.toString())
        }
    }

    fun onFacilityWaningClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            reportPlaceViewModel.clickWarningBtn(checked, view.text.toString())
        }
    }

    fun postReportFacility(view: View) {
        reportPlaceViewModel.postReportFacility(facilityType, facilityAddress, facilityLatitude, facilityLongitude)
    }
}