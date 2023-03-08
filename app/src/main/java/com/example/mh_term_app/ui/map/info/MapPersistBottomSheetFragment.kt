package com.example.mh_term_app.ui.map.info

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.data.model.response.ResponseCategoryPlace
import com.example.mh_term_app.databinding.LayoutInfoCollapseBinding
import com.example.mh_term_app.databinding.LayoutInfoExpandBinding
import com.example.mh_term_app.ui.map.MapViewModel
import com.example.mh_term_app.ui.map.details.DetailChargingStationFragment
import com.example.mh_term_app.ui.map.details.DetailReportFacilityDataFragment
import com.example.mh_term_app.ui.map.details.DetailReportStoreDataFragment
import com.example.mh_term_app.ui.map.review.DetailReviewFragment
import com.example.mh_term_app.utils.listener.TabSelectedListener
import com.example.mh_term_app.utils.listener.changeTabsFont
import kr.co.prnd.persistbottomsheetfragment.PersistBottomSheetFragment


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

    private fun initObserver(){
        mapViewModel.placeRating.observe(viewLifecycleOwner){
            if(it != 0f){
                var rating = ""

                rating = if(it.isNaN()) "-"
                else it.toString()

                collapseBinding.apply {
                    rbBottomInfo.rating = it
                    txtBottomInfoRating.text = rating.toString()
                }
                expandBinding.apply {
                    rbDetailInfo.rating = it
                    txtDetailRating.text = rating.toString()
                }
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
                else -> DetailChargingStationFragment(item.id)
            },
            DetailReviewFragment(item)
        )

        expandBinding.vpInfoDetail.adapter = viewPagerAdapter
    }

    private fun initTab(type: String){
        expandBinding.tlInfoDetail.setupWithViewPager(expandBinding.vpInfoDetail)
        expandBinding.tlInfoDetail.apply {
            getTabAt(0)?.text = if(type == "매장") MHApplication.getApplicationContext().getString(R.string.txt_store_info) else MHApplication.getApplicationContext().getString(R.string.txt_facility_info)
            getTabAt(1)?.text = MHApplication.getApplicationContext().getString(R.string.txt_review)
        }
        expandBinding.tlInfoDetail.addOnTabSelectedListener(TabSelectedListener(expandBinding.tlInfoDetail))
        expandBinding.tlInfoDetail.changeTabsFont(0)
    }

    fun setPlaceData(item : ResponseCategoryPlace){
        placeId = item.id

        collapseBinding.item = item
        collapseBinding.apply {
            txtBottomInfoName.text = when(item.data.type){
                "매장" -> item.data.name
                "시설물" -> item.data.location + " | " + item.data.detailType
                else -> ""
            }
        }
    }

    fun setPlaceDetailData(item : ResponseCategoryPlace) {
        expandBinding.item = item

        when (item.data.type) {
            "매장" -> {
                setTypeName(item.data.detailType, item.data.name)

            }
            "시설물" -> {
                setTypeName(item.data.location, item.data.detailType)
            }
        }
        initViewPager(item)
        initTab(item.data.type)
    }

    private fun setTypeName(type:String, name:String){
        expandBinding.txtDetailType.text = type
        expandBinding.txtDetailName.text = name
    }

    fun setPlaceRating(id:String){
        mapViewModel.getPlaceRating(id)
    }

}