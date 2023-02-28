package com.example.mh_term_app.ui.map.details

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.Time
import com.example.mh_term_app.data.model.request.RequestPlaceStore
import com.example.mh_term_app.databinding.FragmentDetailReportStoreDataBinding
import com.example.mh_term_app.databinding.ViewPlaceInfoItemNoneBinding
import com.example.mh_term_app.ui.map.MapViewModel
import com.example.mh_term_app.ui.map.details.update.UpdatePlaceInfoActivity
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
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

    override fun initListener() {
        super.initListener()

        goToUpdatePlaceInfo(binding.inStoreDetailWeekdayNone)
        goToUpdatePlaceInfo(binding.inStoreDetailSaturdayNone)
        goToUpdatePlaceInfo(binding.inStoreDetailMondayNone)
        goToUpdatePlaceInfo(binding.inStoreDetailPhoneNone)
        goToUpdatePlaceInfo(binding.inStoreDetailTargetNone)
        goToUpdatePlaceInfo(binding.inStoreDetailWarningNone)
    }

    private fun goToUpdatePlaceInfo(view : ViewPlaceInfoItemNoneBinding){
        view.btnAddInfo.setSingleOnClickListener {
            startActivity(Intent(context,UpdatePlaceInfoActivity::class.java))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initObserver() {
        super.initObserver()

        mapViewModel.storeInfo.observe(this){
            binding.apply {
                item = it

                checkData(it)

                txtStoreDetailWeekdayTime.text = setStoreTime(it.time.weekTime, "weekday")
                txtStoreDetailSaturdayTime.text = setStoreTime(it.time.saturdayTime, "saturday")
                txtStoreDetailMondayTime.text = setStoreTime(it.time.mondayTime, "monday")

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

    private fun setStoreTime(time : Time, type: String) : String {
        return when(time.openHourTxt){
            "-1" -> "휴무"
            "-2" -> {
                setDataVisibility(type)
                ""
            }
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

    private fun setDataVisibility(type: String){
        when(type){
            "weekday" -> binding.inStoreDetailWeekdayNone.root.visibility = View.VISIBLE
            "saturday" -> binding.inStoreDetailSaturdayNone.root.visibility = View.VISIBLE
            "monday" -> binding.inStoreDetailMondayNone.root.visibility = View.VISIBLE
            "phone" -> {
                binding.inStoreDetailPhoneNone.root.visibility = View.VISIBLE
                binding.txtStoreDetailPhone.visibility = View.INVISIBLE
            }
            "target" -> {
                binding.rvStoreDetailTarget.visibility = View.INVISIBLE
                binding.inStoreDetailTargetNone.root.visibility = View.VISIBLE
            }
            "warning" -> {
                binding.rvStoreDetailWarning.visibility = View.INVISIBLE
                binding.inStoreDetailWarningNone.root.visibility = View.VISIBLE
            }
        }
    }

    private fun checkData(data : RequestPlaceStore){
        if(data.phone == "none") setDataVisibility("phone")
        if(data.targetList == null) setDataVisibility("target")
        if(data.warningList == null) setDataVisibility("warning")
    }
}