package com.example.mh_term_app.ui.menu.report

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityReportPlaceBinding
import com.example.mh_term_app.utils.extension.changeKeywordColor
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.extension.toast
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons


class ReportPlaceActivity : BaseActivity<ActivityReportPlaceBinding>(), OnMapReadyCallback {
    override val layoutResID: Int
        get() = R.layout.activity_report_place
    private val reportPlaceViewModel : ReportViewModel by viewModels()
    private lateinit var mapFragment : MapFragment

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

        val fm = supportFragmentManager
        mapFragment = fm.findFragmentById(R.id.fl_report_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.fl_report_map, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        naverMap.addOnCameraIdleListener {
            toast("카메라 움직임 종료")
            Log.d("명",naverMap.cameraPosition.toString())
        }
    }

    override fun initObserver() {
        super.initObserver()

        setPlaceObserver(reportPlaceViewModel.addressTxt)
        setPlaceObserver(reportPlaceViewModel.typeTxt)
    }

    private fun setPlaceObserver(data : LiveData<String>){
        data.observe(this){
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
        val className : Class<*> = if(binding.rbReportTypeStore.isChecked) {
            ReportInfoStoreActivity::class.java
        } else {
            ReportInfoFacilityActivity::class.java
        }

        val reportIntent = Intent(this, className)
        reportIntent.putExtra("type", reportPlaceViewModel.typeTxt.value)
        reportIntent.putExtra("address", reportPlaceViewModel.addressTxt.value)
        startActivity(reportIntent)
    }
}