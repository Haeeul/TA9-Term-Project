package com.example.mh_term_app.ui.map.details.update

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.fragment.app.viewModels
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.ReportPlaceAddress
import com.example.mh_term_app.databinding.FragmentUpdateAddressBinding
import com.example.mh_term_app.ui.menu.report.ReportViewModel
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.extension.toast
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource

class UpdateAddressFragment(private val placeAddressInfo : ReportPlaceAddress) : BaseFragment<FragmentUpdateAddressBinding>(),
    OnMapReadyCallback {
    override val layoutResID: Int
        get() = R.layout.fragment_update_address
    private val reportPlaceViewModel: ReportViewModel by viewModels()

    private lateinit var locationSource: FusedLocationSource
    private lateinit var mapFragment: MapFragment
    private lateinit var naverMap: NaverMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationSource =
            FusedLocationSource(this, MainActivity.LOCATION_PERMISSION_REQUEST_CODE)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        super.initView()
        binding.apply {
            vm = reportPlaceViewModel
        }

        val fm = childFragmentManager
        mapFragment = fm.findFragmentById(R.id.fl_update_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.fl_update_map, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) { // 권한 거부됨
                requireContext().toast("위치 권한을 허용해주세요")
                naverMap.locationTrackingMode = LocationTrackingMode.None
                naverMap.locationOverlay.isVisible = false
            } else {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
                naverMap.locationOverlay.isVisible = true
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true

        naverMap.addOnCameraIdleListener {
            reportPlaceViewModel.getAddress(
                naverMap.cameraPosition.target.latitude,
                naverMap.cameraPosition.target.longitude
            )
        }

        val cameraPosition = CameraPosition(
            LatLng(
                placeAddressInfo.latitude,
                placeAddressInfo.longitude
            ), 18.0
        )
        naverMap.cameraPosition = cameraPosition

        setAddressInfo()
    }

    private fun setAddressInfo(){
        binding.edtUpdatePlaceAddress.setText(placeAddressInfo.address)

        if(placeAddressInfo.detailAddress != "none") binding.edtUpdatePlaceAddressDetail.setText(placeAddressInfo.detailAddress)
        else binding.edtUpdatePlaceAddressDetail.setText("")
    }

    override fun initListener() {
        super.initListener()

        binding.btnUpdatePlaceSend.setSingleOnClickListener {
            Log.d("명",reportPlaceViewModel.addressTxt.value.toString() + " / " +
                    reportPlaceViewModel.detailAddressTxt.value.toString() + " / " +
                    this.naverMap.cameraPosition.target.toString())
        }
    }
}