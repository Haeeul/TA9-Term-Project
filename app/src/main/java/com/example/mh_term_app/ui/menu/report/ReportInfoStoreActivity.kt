package com.example.mh_term_app.ui.menu.report

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityReportInfoStoreBinding
import com.example.mh_term_app.utils.extension.*

class ReportInfoStoreActivity : BaseActivity<ActivityReportInfoStoreBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_report_info_store
    private val reportPlaceViewModel : ReportViewModel by viewModels()

    private var storeType = ""
    private var storeAddress = ""
    private var storeLatitude = ""
    private var storeLongitude = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storeType = intent.getStringExtra("type").toString()
        storeAddress = intent.getStringExtra("address").toString()
        storeLatitude = intent.getStringExtra("latitude").toString()
        storeLongitude = intent.getStringExtra("longitude").toString()

        binding.apply {
            vm = reportPlaceViewModel
            txtReportInfoStoreTitle.changeKeywordColor(
                MHApplication.getApplicationContext().getString(R.string.desc_report_type_store_start),
                MHApplication.getApplicationContext().getString(R.string.desc_report_type_store_end),
                7,9,2,5
            )
        }
    }

    override fun initView() {
        super.initView()

        binding.tbReportInfoStore.apply {
            title = MHApplication.getApplicationContext().getString(R.string.title_menu_report)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }
    }

    override fun initObserver() {
        super.initObserver()

        setStoreObserver(reportPlaceViewModel.storeNameTxt)
        setStoreObserver(reportPlaceViewModel.detailTypeTxt)
        setStoreObserver(reportPlaceViewModel.plusInfoTxt)

        reportPlaceViewModel.isValidPost.observe(this){
            if(it) toast("제보 완료")
            else errorToast()
            startActivityWithAffinity(MainActivity::class.java)
        }
    }

    private fun setStoreObserver(data : LiveData<String>){
        data.observe(this){
            reportPlaceViewModel.checkValidStoreCompleteBtn()
        }
    }

    override fun initListener() {
        super.initListener()

        onClickTimeListener("week", binding.txtReportInfoStoreWeekTime)
        onClickTimeListener("saturday", binding.txtReportInfoStoreSaturdayTime)
        onClickTimeListener("monday", binding.txtReportInfoStoreMondayTime)

        getStoreDetailType()
    }

    private fun onClickTimeListener(type: String, txt : TextView){
        txt.setSingleOnClickListener {
            createStoreTimeDialog(supportFragmentManager, type, reportPlaceViewModel)
        }
    }

    private fun getStoreDetailType() {
        binding.rgReportInfoStoreType.setOnCheckedChangeListener{ _, checkedId ->
            when (checkedId) {
                R.id.rb_store_restaurant -> reportPlaceViewModel.setDetailTypeTxt(getString(R.string.txt_restaurant))
                R.id.rb_store_cafe -> reportPlaceViewModel.setDetailTypeTxt(getString(R.string.txt_cafe))
            }
        }
    }

    fun onStoreTargetClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            reportPlaceViewModel.clickTargetBtn(checked, view.text.toString())
        }
    }

    fun onStoreWaningClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            reportPlaceViewModel.clickWarningBtn(checked, view.text.toString())
        }
    }

    fun postReportStore(view: View){
        reportPlaceViewModel.postReportStore(storeType,storeAddress)
    }

}