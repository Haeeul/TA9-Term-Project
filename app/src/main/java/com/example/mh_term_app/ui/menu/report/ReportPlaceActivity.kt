package com.example.mh_term_app.ui.menu.report

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityReportPlaceBinding
import com.example.mh_term_app.utils.extension.changeKeywordColor
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.extension.toast
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import java.io.IOException
import java.util.*


class ReportPlaceActivity : BaseActivity<ActivityReportPlaceBinding>(), OnMapReadyCallback {
    override val layoutResID: Int
        get() = R.layout.activity_report_place
    private val reportPlaceViewModel : ReportViewModel by viewModels()

    private lateinit var locationSource: FusedLocationSource
    private lateinit var mapFragment : MapFragment
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            vm = reportPlaceViewModel
            txtReportPlaceTitle.changeKeywordColor(
                MHApplication.getApplicationContext().getString(R.string.desc_report_place_start),
                MHApplication.getApplicationContext().getString(R.string.desc_report_place_end),
                10, 12, 2,4)
        }

        locationSource =
            FusedLocationSource(this, MainActivity.LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                toast("위치 권한을 허용해주세요")
                naverMap.locationTrackingMode = LocationTrackingMode.None
                naverMap.locationOverlay.isVisible = false
            }else{
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
                naverMap.locationOverlay.isVisible = true
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        checkPermission()

        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true

        naverMap.addOnCameraIdleListener  {
//            toast(naverMap.cameraPosition.toString())
            Log.d("명1",naverMap.cameraPosition.toString())

            Log.d("명2",getAddress(naverMap.cameraPosition.target.latitude, naverMap.cameraPosition.target.longitude))
            binding.edtReportPlaceAddress.setText(getAddress(naverMap.cameraPosition.target.latitude, naverMap.cameraPosition.target.longitude))
        }
    }

    private fun checkPermission(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        else
            naverMap.locationTrackingMode = LocationTrackingMode.None
    }

    private fun getAddress(lat: Double, lng: Double): String {
        val geoCoder = Geocoder(this, Locale.KOREA)
        val address: ArrayList<Address>
        var addressResult = "주소를 가져 올 수 없습니다."
        try {
            //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
            //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
            address = geoCoder.getFromLocation(lat, lng, 1) as ArrayList<Address>
            if (address.size > 0) {
                // 주소 받아오기
                val currentLocationAddress = address[0].getAddressLine(0)
                    .toString()
                addressResult = currentLocationAddress

            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addressResult
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