package com.example.mh_term_app

import androidx.annotation.UiThread
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityMainBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker

class MainActivity : BaseActivity<ActivityMainBinding>(), OnMapReadyCallback {
    override val layoutResID = R.layout.activity_main

    override fun initView() {
        initMap()
    }

    private fun initMap(){
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.fl_main_container) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.fl_main_container, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        // ...
        val marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)
//        marker.icon = MarkerIcons.BLACK
//        marker.iconTintColor = Color.RED
        marker.map = naverMap
    }
}