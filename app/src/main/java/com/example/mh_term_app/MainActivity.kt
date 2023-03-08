package com.example.mh_term_app

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.data.model.MarkerList
import com.example.mh_term_app.data.model.response.ResponseCategoryPlace
import com.example.mh_term_app.databinding.ActivityMainBinding
import com.example.mh_term_app.ui.map.MapViewModel
import com.example.mh_term_app.ui.map.info.MapPersistBottomSheetFragment
import com.example.mh_term_app.utils.extension.toast
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.NaverMap.LAYER_GROUP_TRANSIT
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons


class MainActivity : BaseActivity<ActivityMainBinding>(), OnMapReadyCallback{
    private final var FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0

    override val layoutResID = R.layout.activity_main
    private val mapViewModel: MapViewModel by viewModels()

    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private var categoryMarkers : MutableList<MarkerList<ResponseCategoryPlace>> = mutableListOf()

    private var mapPersistBottomFragment: MapPersistBottomSheetFragment? = null
    private lateinit var mapFragment : MapFragment

    private lateinit var navController: NavController

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

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

    private fun initNavigation(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    private fun setNaverMap(){
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
        checkPermission()

        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true

        val cameraPosition = CameraPosition(
            LatLng(
                naverMap.cameraPosition.target.latitude,
                naverMap.cameraPosition.target.longitude
            ), 15.0
        )
        naverMap.cameraPosition = cameraPosition

//        naverMap.mapType = NaverMap.MapType.Basic
        naverMap.setLayerGroupEnabled(LAYER_GROUP_TRANSIT, true)

        naverMap.setOnMapClickListener { _, _ ->
            setInfoWindowVisibility(false)
        }
    }

    fun setCategoryMarkerList(data: MutableList<ResponseCategoryPlace>){
        resetMarkers()

        data.forEach {
            val marker = Marker().apply {
                position = LatLng(it.data.latitude, it.data.longitude)
                icon = MarkerIcons.RED
                icon = MarkerIcons.BLACK
                iconTintColor = setMarkerColor(it.data.type)
            }
            categoryMarkers.add(MarkerList(marker,it))
        }

        categoryMarkers.forEach {
            it.marker.apply {
                map = naverMap
                onClickListener = setStoreMarkerClickListener(it.data, position)
            }
        }

        if(data.isNotEmpty()){
            val cameraUpdate = CameraUpdate.scrollTo(LatLng(data[0].data.latitude,data[0].data.longitude))
                .animate(CameraAnimation.Fly, 2000)
            naverMap.moveCamera(cameraUpdate)

            mapPersistBottomFragment?.apply {
                setPlaceData(data[0])
            }
            setInfoWindowVisibility(true)
        }

    }

    fun setSearchPlaceMarker(data: ResponseCategoryPlace){
        setCategoryMarkerList(mutableListOf(data))

        mapPersistBottomFragment?.apply {
            setPlaceData(data)
        }
        setInfoWindowVisibility(true)

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(data.data.latitude,data.data.longitude))
            .animate(CameraAnimation.Fly, 2000)
        naverMap.moveCamera(cameraUpdate)
    }

    private fun resetMarkers(){
        categoryMarkers.forEach {
            it.marker.apply {
                map = null
            }
        }

        categoryMarkers.clear()
    }

    private fun setMarkerColor(type: String) : Int{
        return when(type){
            "매장" -> Color.RED
            "시설물" -> ContextCompat.getColor(this, R.color.yellow)
            "충전소" -> ContextCompat.getColor(this, R.color.green)
            "이동지원센터" -> ContextCompat.getColor(this, R.color.pink)
            "화장실" -> ContextCompat.getColor(this, R.color.beige)
            else -> Color.BLACK
        }
    }

    private fun checkPermission(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        else
            naverMap.locationTrackingMode = LocationTrackingMode.None
    }

    private fun setStoreMarkerClickListener(data : ResponseCategoryPlace, latLng: LatLng): Overlay.OnClickListener {
        return Overlay.OnClickListener { overlay ->
            mapPersistBottomFragment?.apply {
                setPlaceData(data)
            }
            setInfoWindowVisibility(true)

            val cameraUpdate = CameraUpdate.scrollTo(latLng)
                .animate(CameraAnimation.Fly, 1000)
            naverMap.moveCamera(cameraUpdate)

            true
        }
    }

    fun setInfoWindowVisibility(isValid : Boolean){
        if(isValid) binding.flBottomContainer.visibility = View.VISIBLE
        else binding.flBottomContainer.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (mapPersistBottomFragment?.handleBackKeyEvent() == true) {

        } else {
            if (binding.flBottomContainer.visibility == View.VISIBLE) {
                setInfoWindowVisibility(false)
            }else if(binding.flMainContainer.findViewById<DrawerLayout>(R.id.drawerLayout) != null &&
                binding.flMainContainer.findViewById<DrawerLayout>(R.id.drawerLayout).isDrawerOpen(GravityCompat.START)){
                binding.flMainContainer.findViewById<DrawerLayout>(R.id.drawerLayout).closeDrawers()
            }else {
                if (supportFragmentManager.backStackEntryCount == 0) {
                    var tempTime = System.currentTimeMillis()
                    var intervalTime = tempTime - backPressedTime
                    if (intervalTime in 0..FINISH_INTERVAL_TIME) {
                        super.onBackPressed()
                    } else {
                        backPressedTime = tempTime
                        toast("'뒤로' 버튼을 한 번 더 누르면 종료됩니다.")
                        return
                    }
                }
                super.onBackPressed()
            }
        }
    }

    fun goToSearchListener(){
        navController.navigate(R.id.action_naverMapFragment_to_searchFragment)
    }
}