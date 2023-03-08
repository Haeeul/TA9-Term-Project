package com.example.mh_term_app.ui.map.details.update

import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.data.model.*
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
    private lateinit var storeDetailInfo : UpdateStoreInfo
    private lateinit var facilityDetailInfo : UpdateFacilityInfo
    private var placeId = ""

    override fun initView() {
        super.initView()

        placeId = intent.getStringExtra("id").toString()
        placeAddressInfo = intent.getParcelableExtra<ReportPlaceAddress>("placeAddressInfo")!!
        getDetailInfo(placeAddressInfo.type)

        binding.tbUptaePlaceInfo.apply {
            title = getString(R.string.title_update_place_info)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }

        initViewPager(placeAddressInfo.type)
        initTab(placeAddressInfo.type)
    }

    private fun getDetailInfo(type: String){
        when(type){
            "매장" -> storeDetailInfo = changeDataType(intent.getParcelableExtra<OriginStoreInfo>("storeDetailInfo")!!)
            "시설물" -> facilityDetailInfo = intent.getParcelableExtra<UpdateFacilityInfo>("facilityDetailInfo")!!
        }
    }

    private fun changeDataType(originStoreInfo: OriginStoreInfo) : UpdateStoreInfo{
        return UpdateStoreInfo(
            originStoreInfo.name,
            originStoreInfo.phone,
            StoreTime(
                changeToTimeData(originStoreInfo.time!![0]),
                changeToTimeData(originStoreInfo.time[1]),
                changeToTimeData(originStoreInfo.time[2])
            ),
            originStoreInfo.detailType,
            originStoreInfo.targetList,
            originStoreInfo.warningList,
            originStoreInfo.plusInfo
        )
    }

    private fun changeToTimeData(data : MutableList<String>): Time {
        return Time(data[0],data[1],data[2],data[3])
    }

    private fun initViewPager(type: String){
        viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager
        )
        viewPagerAdapter.fragments = listOf(
            if(type == "매장") UpdateStoreInfoFragment(placeId,storeDetailInfo) else UpdateFacilityInfoFragment(placeId,facilityDetailInfo),
            UpdateAddressFragment(placeId, placeAddressInfo)
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

    fun goToBack(){
        finish()
    }
}