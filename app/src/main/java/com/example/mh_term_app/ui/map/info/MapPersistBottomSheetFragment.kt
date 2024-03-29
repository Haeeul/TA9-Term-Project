package com.example.mh_term_app.ui.map.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.data.model.response.PlaceBasicInfo
import com.example.mh_term_app.data.model.response.ResponseCategoryPlace
import com.example.mh_term_app.databinding.LayoutInfoCollapseBinding
import com.example.mh_term_app.databinding.LayoutInfoExpandBinding
import com.example.mh_term_app.ui.map.MapViewModel
import com.example.mh_term_app.ui.map.details.open.DetailChargingStationFragment
import com.example.mh_term_app.ui.map.details.open.DetailMoveCenterFragment
import com.example.mh_term_app.ui.map.details.open.DetailPublicToiletFragment
import com.example.mh_term_app.ui.map.details.report.DetailReportFacilityDataFragment
import com.example.mh_term_app.ui.map.details.report.DetailReportStoreDataFragment
import com.example.mh_term_app.ui.map.details.review.DetailReviewFragment
import com.example.mh_term_app.utils.databinding.BindingAdapter.setCallIcon
import com.example.mh_term_app.utils.databinding.BindingAdapter.setCallTxt
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.extension.toast
import com.example.mh_term_app.utils.listener.TabSelectedListener
import com.example.mh_term_app.utils.listener.changeTabsFont
import kr.co.prnd.persistbottomsheetfragment.PersistBottomSheetFragment
import java.math.RoundingMode
import java.text.DecimalFormat


class MapPersistBottomSheetFragment() : PersistBottomSheetFragment<LayoutInfoCollapseBinding, LayoutInfoExpandBinding>(
    R.layout.layout_info_collapse,
    R.layout.layout_info_expand
) {
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var markerId : String
    private val mapViewModel : MapViewModel by viewModels()

    var placeId = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        collapseBinding.vm = mapViewModel
    }

    companion object {
        private val TAG = MapPersistBottomSheetFragment::class.simpleName

        fun show(
            fragmentManager: FragmentManager,
            @IdRes containerViewId: Int,
        ): MapPersistBottomSheetFragment =
            fragmentManager.findFragmentByTag(TAG) as? MapPersistBottomSheetFragment
                ?: MapPersistBottomSheetFragment().apply {
                    fragmentManager.beginTransaction()
                        .replace(containerViewId, this, TAG)
                        .commitAllowingStateLoss()
                }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun initObserver(){
        mapViewModel.placeRating.observe(viewLifecycleOwner){
            if(it != 0f){
                var rating = ""

                rating = if(it.isNaN()) "-"
                else {
                    val df = DecimalFormat("#.#")
                    df.roundingMode = RoundingMode.DOWN

                    if(df.format(it).length == 1) df.format(it)+".0"
                    else df.format(it)
                }

                collapseBinding.apply {
                    rbBottomInfo.rating = it
                    txtBottomInfoRating.text = rating.toString()
                }
                expandBinding.apply {
                    rbDetailInfo.rating = it
                    txtDetailRating.text = rating.toString()
                }
                mapViewModel.setLoading(false)
            }else mapViewModel.getPlaceRating(placeId)
        }
    }

    private fun initViewPager(item: ResponseCategoryPlace){
        viewPagerAdapter = ViewPagerAdapter(
            childFragmentManager
        )
        viewPagerAdapter.fragments = listOf(
            when(item.data.type){
                "매장" -> DetailReportStoreDataFragment(item.id)
                "시설물" -> DetailReportFacilityDataFragment(item.id)
                "충전소" -> DetailChargingStationFragment(item.id)
                "이동지원센터" -> DetailMoveCenterFragment(item.id)
                else -> DetailPublicToiletFragment(item.id)
            },
            DetailReviewFragment(item)
        )

        expandBinding.vpInfoDetail.adapter = viewPagerAdapter
    }

    private fun initTab(type: String){
        expandBinding.tlInfoDetail.setupWithViewPager(expandBinding.vpInfoDetail)
        expandBinding.tlInfoDetail.apply {
            getTabAt(0)?.text = when(type) {
                "매장" -> getString(R.string.txt_store_info)
                "시설물" -> getString(R.string.txt_facility_info)
                "충전소" -> getString(R.string.txt_charging_info)
                "이동지원센터" -> getString(R.string.txt_center_info)
                else -> "화장실 정보"
            }
            getTabAt(1)?.text = getString(R.string.txt_review)

        }
        expandBinding.tlInfoDetail.addOnTabSelectedListener(TabSelectedListener(expandBinding.tlInfoDetail))
        expandBinding.tlInfoDetail.changeTabsFont(0)
    }

    fun setPlaceData(item : ResponseCategoryPlace){
        Log.d("명",item.data.toString())
        setPlaceRating(item.id)
        setCallBtnVisibility(item.data.phone)
        setPlaceInfo(item)
        setPlaceDetailData(item)
        initListener()
    }

    fun setBasicData(item : PlaceBasicInfo){
        placeId = item.id

        collapseBinding.apply {
            txtBottomInfoName.text = when(item.type){
                "시설물" -> item.location + " | " + item.detailType
                else -> item.name
            }
        }

        collapseBinding.btnBottomInfoCall.setSingleOnClickListener {
            goToCall(item.phone)
        }

        when (item.type) {
            "매장" -> setTypeName(item.detailType, item.name)
            "시설물" -> setTypeName(item.location, item.detailType)
            else -> setTypeName(item.type, item.name)
        }

        setCallBtnVisibility(item.phone)

        expandBinding.btnDetailCall.setSingleOnClickListener {
            goToCall(item.phone)
        }
    }

    private fun setPlaceInfo(item : ResponseCategoryPlace){
        placeId = item.id

        collapseBinding.item = item
        collapseBinding.apply {
            txtBottomInfoName.text = when(item.data.type){
                "시설물" -> item.data.location + " | " + item.data.detailType
                else -> item.data.name
            }
        }

        collapseBinding.btnBottomInfoCall.setSingleOnClickListener {
            goToCall(item.data.phone)
        }
    }

    private fun setPlaceDetailData(item : ResponseCategoryPlace) {
        expandBinding.item = item

        when (item.data.type) {
            "매장" -> setTypeName(item.data.detailType, item.data.name)
            "시설물" -> setTypeName(item.data.location, item.data.detailType)
            else -> setTypeName(item.data.type, item.data.name)
        }

        initViewPager(item)
        initTab(item.data.type)

        expandBinding.btnDetailCall.setSingleOnClickListener {
            goToCall(item.data.phone)
        }
    }

    private fun setTypeName(type:String, name:String){
        expandBinding.txtDetailType.text = type
        expandBinding.txtDetailName.text = name
    }

    private fun setPlaceRating(id:String){
        mapViewModel.getPlaceRating(id)
        mapViewModel.setLoading(true)
    }

    private fun setCallBtnVisibility(phoneNum : String){
        collapseBinding.btnBottomInfoCall.setCallIcon(phoneNum)
        expandBinding.apply {
            btnDetailCall.setCallIcon(phoneNum)
            txtDetailCall.setCallTxt(phoneNum)
            imgExpandLine.setCallIcon(phoneNum)
        }
    }

    private fun goToCall(phoneNum : String){
//        if(phoneNum == "none") context?.toast(getString(R.string.txt_phone_number_none))
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phoneNum")
        startActivity(callIntent)
    }

    private fun initListener(){
        collapseBinding.btnBottomInfoFavorite.setSingleOnClickListener {
            requireContext().toast(getString(R.string.txt_anvil_feature))
        }
        expandBinding.btnDetailFavorite.setSingleOnClickListener {
            requireContext().toast(getString(R.string.txt_anvil_feature))
        }
        expandBinding.btnDetailBack.setSingleOnClickListener {
            handleBackKeyEvent()
        }
    }

}