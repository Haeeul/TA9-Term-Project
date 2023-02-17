package com.example.mh_term_app.ui.map

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.mh_term_app.R
import com.example.mh_term_app.databinding.LayoutInfoCollapseBinding
import com.example.mh_term_app.databinding.LayoutInfoExpandBinding
import com.example.mh_term_app.utils.listener.TabSelectedListener
import com.example.mh_term_app.utils.listener.changeTabsFont
import kr.co.prnd.persistbottomsheetfragment.PersistBottomSheetFragment

class MapPersistBottomSheetFragment : PersistBottomSheetFragment<LayoutInfoCollapseBinding, LayoutInfoExpandBinding>(
    R.layout.layout_info_collapse,
    R.layout.layout_info_expand
) {
    lateinit var viewPagerAdapter: ViewPagerAdapter

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
            ReportDataDetailFragment(),
            OpenDataDetailFragment()
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
}