package com.example.mh_term_app.ui.map.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.Time
import com.example.mh_term_app.databinding.FragmentDetailReportStoreDataBinding
import com.example.mh_term_app.ui.map.MapViewModel
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager


class DetailReportStoreDataFragment(private val storeId : String) : BaseFragment<FragmentDetailReportStoreDataBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_detail_report_store_data

    private var rvStoreTargetAdapter = DetailChipAdapter()
    private var rvStoreWarningAdapter = DetailChipAdapter()
    private val mapViewModel : MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapViewModel.getStoreInfo(storeId)
    }

    override fun initView() {
        super.initView()

        initRv()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initObserver() {
        super.initObserver()

        mapViewModel.storeInfo.observe(this){
            binding.apply {
                item = it
                txtStoreDetailWeekdayTime.text = setStoreTime(it.time.weekTime)
                txtStoreDetailSaturdayTime.text = setStoreTime(it.time.saturdayTime)
                txtStoreDetailMondayTime.text = setStoreTime(it.time.mondayTime)



                rvStoreTargetAdapter.run {
                    replaceAll(it.targetList as ArrayList<String>?)
                    notifyDataSetChanged()
                }

                rvStoreWarningAdapter.run {
                    replaceAll(it.warningList as ArrayList<String>?)
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun initRv(){
        val targetRvLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        val warningRvLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            alignItems = AlignItems.STRETCH
        }

        binding.rvStoreDetailTarget.run {
            adapter = rvStoreTargetAdapter
            layoutManager = targetRvLayoutManager
        }

        binding.rvStoreDetailWarning.run {
            adapter = rvStoreWarningAdapter
            layoutManager = warningRvLayoutManager
        }
    }

    private fun setStoreTime(time : Time) : String {
        return when(time.openHourTxt){
            "-1" -> "휴무"
            "-2" -> "정보 없음"
            else -> {
                if(time.openMinuteTxt == "0" && time.closeHourTxt == "11" && time.closeMinuteTxt == "59") "24시간 영업"
                else setTimeFormat(time.openHourTxt) + " : " + setTimeFormat(time.openMinuteTxt) + " ~ " +
                        setTimeFormat(time.closeHourTxt) + " : " + setTimeFormat(time.closeMinuteTxt)
            }
        }
    }

    private fun setTimeFormat(time : String): String {
        return if(time.length == 1) "0$time"
        else time
    }
}