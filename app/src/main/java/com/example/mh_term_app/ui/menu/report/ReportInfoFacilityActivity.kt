package com.example.mh_term_app.ui.menu.report

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.data.model.ReportPlaceAddress
import com.example.mh_term_app.databinding.ActivityReportInfoFacilityBinding
import com.example.mh_term_app.utils.extension.*


class ReportInfoFacilityActivity : BaseActivity<ActivityReportInfoFacilityBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_report_info_facility
    private val reportPlaceViewModel: ReportViewModel by viewModels()

    lateinit var facilityInfo : ReportPlaceAddress
    private lateinit var detailTypeButtons : List<RadioButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        facilityInfo = intent.getParcelableExtra<ReportPlaceAddress>("placeAddressInfo")!!

        detailTypeButtons = listOf(
            binding.rbFacilityBollard,binding.rbFacilityStreet,binding.rbFacilityBlock,
            binding.rbFacilityCarArea,binding.rbFacilityCrosswalk,binding.rbFacilityPass
        )

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

        setRadioBtnListener(binding.rbFacilityBollard)
        setRadioBtnListener(binding.rbFacilityStreet)
        setRadioBtnListener(binding.rbFacilityBlock)
        setRadioBtnListener(binding.rbFacilityCarArea)
        setRadioBtnListener(binding.rbFacilityCrosswalk)
        setRadioBtnListener(binding.rbFacilityPass)
    }

    private fun getFacilityDetailLocation() {
        binding.rgLocationFacility.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_facility_inside -> reportPlaceViewModel.locationTxt.value = (getString(R.string.txt_inside))
                R.id.rb_facility_outside -> reportPlaceViewModel.locationTxt.value = (getString(R.string.txt_outside))
            }
        }
    }

    private fun setRadioBtnListener(button : RadioButton){
        button.setSingleOnClickListener { button
            detailTypeButtons.forEach {
                it.isChecked = it.id == button.id
            }
            reportPlaceViewModel.setDetailTypeTxt(button.text.toString())
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
        reportPlaceViewModel.postReportFacility(facilityInfo)
    }
}