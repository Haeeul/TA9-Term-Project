package com.example.mh_term_app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityMainBinding
import com.example.mh_term_app.ui.map.MapViewModel
import com.example.mh_term_app.ui.map.details.MapPersistBottomSheetFragment
import com.example.mh_term_app.utils.extension.toast
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons


class MainActivity : BaseActivity<ActivityMainBinding>(), OnMapReadyCallback{
    override val layoutResID = R.layout.activity_main
    private val mapViewModel: MapViewModel by viewModels()

    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    private var mapPersistBottomFragment: MapPersistBottomSheetFragment? = null
    private lateinit var mapFragment : MapFragment
    lateinit var marker : Marker

    private lateinit var navController: NavController

    var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        Log.d("onCreate  locationSource.isActivated","${locationSource.isActivated}")

        mapPersistBottomFragment =
            MapPersistBottomSheetFragment.show(supportFragmentManager, R.id.fl_bottom_container)
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
                Log.d("명","2")
                toast("2")
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
                naverMap.locationOverlay.isVisible = true
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun initView() {
        super.initView()

        initNavigation()
        setNaverMap()
    }

    fun setNaverMap(){
        val fm = supportFragmentManager
        mapFragment = fm.findFragmentById(R.id.fm_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.fm_map, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        Log.d("onMapReady  naverMap.locationSource","${locationSource.isActivated}")
        if(locationSource.isActivated){
            Log.d("명","3")
            toast("3")
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
            naverMap.locationOverlay.isVisible = true
        }else{
            Log.d("명","4")
            toast("4")
            naverMap.locationTrackingMode = LocationTrackingMode.None
            naverMap.locationOverlay.isVisible = false
        }

        naverMap.locationOverlay.isVisible = true

        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true

        naverMap.addOnLocationChangeListener { location ->
            Toast.makeText(this, "${location.latitude}, ${location.longitude}",
                Toast.LENGTH_SHORT).show()
        }

        marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)
        marker.icon = MarkerIcons.RED
//        marker.icon = OverlayImage.fromResource(R.drawable.ic_facility)
//        marker.width = 60
//        marker.height = 80
//        marker.iconTintColor = Color.RED
        marker.map = naverMap
        marker.onClickListener = setOnMarkerClickListener(marker.position, naverMap)

        naverMap.setOnMapClickListener { _, _ ->
            setInfoWindowVisibility(false)
        }
    }

    private fun setOnMarkerClickListener(latLng: LatLng, naverMap: NaverMap): Overlay.OnClickListener {
        return Overlay.OnClickListener { overlay ->
//            binding.data = mapViewModel.centerData.value?.get(id-1)
            setInfoWindowVisibility(true)

            val marker = overlay as Marker

            val cameraUpdate = CameraUpdate.scrollTo(latLng)
                .animate(CameraAnimation.Fly, 1000)
            naverMap.moveCamera(cameraUpdate)

            true
        }
    }

    fun setInfoWindowVisibility(isValid : Boolean){
//        if(binding.flBottomContainer.visibility == View.VISIBLE) binding.flBottomContainer.visibility =View.GONE
//        else binding.flBottomContainer.visibility = View.VISIBLE
        if(isValid) binding.flBottomContainer.visibility = View.VISIBLE
        else binding.flBottomContainer.visibility = View.GONE
    }

    override fun initListener() {
        super.initListener()

//        binding.btnMapGps.setSingleOnClickListener {
//            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//
////                naverMap.locationTrackingMode = LocationTrackingMode.Follow
//                toast("권한 허용")
//            }
//
//            // 권한이 없을 때 권한을 요구함
//            else {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                        android.Manifest.permission.ACCESS_FINE_LOCATION),
//                    1
//                )
//            }
//        }
    }

    override fun onBackPressed() {
        if (mapPersistBottomFragment?.handleBackKeyEvent() == true) {
            // no-op
        } else {
            super.onBackPressed()
        }

    }

    private fun initNavigation(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    fun goToSearchListener(){
        navController.navigate(R.id.action_naverMapFragment_to_searchFragment)
    }
}