package com.example.mh_term_app.ui.map.details.update

import android.util.Log
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.data.model.ReportPlaceAddress
import com.example.mh_term_app.databinding.ActivityUpdatePlaceInfoBinding
import com.example.mh_term_app.ui.map.info.ViewPagerAdapter
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.listener.TabSelectedListener
import com.example.mh_term_app.utils.listener.changeTabsFont

class UpdatePlaceInfoActivity() : BaseActivity<ActivityUpdatePlaceInfoBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_update_place_info

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var placeAddressInfo : ReportPlaceAddress

    override fun initView() {
        super.initView()

        placeAddressInfo = intent.getParcelableExtra<ReportPlaceAddress>("placeAddressInfo")!!

        Log.d("명명",intent.getParcelableExtra<ReportPlaceAddress>("placeAddressInfo").toString())

        binding.tbUptaePlaceInfo.apply {
            title = MHApplication.getApplicationContext().getString(R.string.title_update_place_info)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }

        initViewPager(placeAddressInfo.type)
        initTab(placeAddressInfo.type)
    }

    private fun initViewPager(type: String){
        viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager
        )
        viewPagerAdapter.fragments = listOf(
            if(type == "매장") UpdateStoreInfoFragment() else UpdateFacilityInfoFragment(),
            UpdateAddressFragment(placeAddressInfo)
        )

        binding.vpUpdatePlaceInfo.adapter = viewPagerAdapter
    }

    private fun initTab(type: String){
        binding.tlUpdatePlaceInfo.setupWithViewPager(binding.vpUpdatePlaceInfo)
        binding.tlUpdatePlaceInfo.apply {
            getTabAt(0)?.text = getTabTxt(type)
            getTabAt(1)?.text = getString(R.string.txt_transfer)
        }
        binding.tlUpdatePlaceInfo.addOnTabSelectedListener(TabSelectedListener(binding.tlUpdatePlaceInfo))
        binding.tlUpdatePlaceInfo.changeTabsFont(0)
    }

    private fun getTabTxt(type: String) : String{
        return when(type){
            "매장" -> getString(R.string.txt_store_info)
            "시설물" -> getString(R.string.txt_facility_info)
            else -> ""
        }
    }
}