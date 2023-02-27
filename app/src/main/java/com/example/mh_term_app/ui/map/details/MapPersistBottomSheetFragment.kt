package com.example.mh_term_app.ui.map.details

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.mh_term_app.R
import com.example.mh_term_app.data.model.response.ResponseCategoryList
import com.example.mh_term_app.databinding.LayoutInfoCollapseBinding
import com.example.mh_term_app.databinding.LayoutInfoExpandBinding
import com.example.mh_term_app.ui.map.ViewPagerAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
        initTab()
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

    private fun initViewPager(){
        viewPagerAdapter = ViewPagerAdapter(
            childFragmentManager
        )
        viewPagerAdapter.fragments = listOf(
            DetailReportStoreDataFragment(),
            DetailReviewFragment()
        )

        expandBinding.vpInfoDetail.adapter = viewPagerAdapter
    }

    private fun initTab(){
        expandBinding.tlInfoDetail.setupWithViewPager(expandBinding.vpInfoDetail)
        expandBinding.tlInfoDetail.apply {
            getTabAt(0)?.text = "시설물 정보"
            getTabAt(1)?.text = "리뷰"
        }
        expandBinding.tlInfoDetail.addOnTabSelectedListener(TabSelectedListener(expandBinding.tlInfoDetail))
        expandBinding.tlInfoDetail.changeTabsFont(0)
    }

    fun setPlaceData(item : ResponseCategoryList){
        collapseBinding.item = item
        collapseBinding.apply {
            txtBottomInfoName.text = when(item.data.type){
                "매장" -> item.data.name
                "시설물" -> item.data.location + " | " + item.data.detailType
                else -> ""
            }
        }
    }

    fun setPlaceDetailData(item : ResponseCategoryList) {
        expandBinding.item = item
        when (item.data.type) {
            "매장" -> {
                setTypeName(item.data.detailType, item.data.name)
            }
            "시설물" -> {
                setTypeName(item.data.location, item.data.detailType)
            }
        }
    }

    private fun setTypeName(type:String, name:String){
        expandBinding.txtDetailType.text = type
        expandBinding.txtDetailName.text = name
    }

}