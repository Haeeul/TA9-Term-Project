package com.example.mh_term_app

import android.os.Bundle
import android.view.View
import androidx.annotation.UiThread
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityMainBinding
import com.example.mh_term_app.ui.map.details.MapPersistBottomSheetFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.MarkerIcons

class MainActivity : BaseActivity<ActivityMainBinding>(), OnMapReadyCallback {
    override val layoutResID = R.layout.activity_main

    private var mapPersistBottomFragment: MapPersistBottomSheetFragment? = null
    private lateinit var mapFragment : MapFragment
    lateinit var marker : Marker

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapPersistBottomFragment =
            MapPersistBottomSheetFragment.show(supportFragmentManager, R.id.fl_bottom_container)
    }

    override fun initView() {
        super.initView()

        initNavigation()

        val fm = supportFragmentManager
        mapFragment = fm.findFragmentById(R.id.fm_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.fm_map, it).commit()
            }

        getNaverMap()
    }

    fun getNaverMap(){
        mapFragment.getMapAsync(this)
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

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        // ...
        marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)
//        marker.icon = OverlayImage.fromResource(R.drawable.ic_facility)
        marker.icon = MarkerIcons.RED
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
}