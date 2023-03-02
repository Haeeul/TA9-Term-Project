package com.example.mh_term_app.ui.map.details

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.request.RequestPlaceFacility
import com.example.mh_term_app.databinding.FragmentDetailReportFacilityDataBinding
import com.example.mh_term_app.databinding.ViewPlaceInfoItemNoneBinding
import com.example.mh_term_app.ui.map.MapViewModel
import com.example.mh_term_app.ui.map.details.update.UpdatePlaceInfoActivity
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class DetailReportFacilityDataFragment(private val facilityId : String) : BaseFragment<FragmentDetailReportFacilityDataBinding>() {
    override val layoutResID: Int
    get() = R.layout.fragment_detail_report_facility_data

    private var rvFacilityTargetAdapter = DetailChipAdapter()
    private var rvFacilityWarningAdapter = DetailChipAdapter()
    private val mapViewModel : MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapViewModel.getFacilityInfo(facilityId)
    }

    override fun initView() {
        super.initView()

        initRv()
    }

    override fun initListener() {
        super.initListener()

        binding.txtUpdateFacilityInfo.setSingleOnClickListener {
            goToUpdatePlaceInfo()
        }

        binding.btnUpdateFacilityInfo.setSingleOnClickListener {
            goToUpdatePlaceInfo()
        }

        setNoneItemListener(binding.inFacilityTargetNone, getString(R.string.desc_add_target_info))
        setNoneItemListener(binding.inFailityDetailWarningNone, getString(R.string.desc_add_warning_info))
    }

    private fun setNoneItemListener(view : ViewPlaceInfoItemNoneBinding, content : String){
        view.btnAddInfo.setSingleOnClickListener {
            goToUpdatePlaceInfo()
        }

        view.txtInfoItemNoneContent.text = content
    }

    private fun goToUpdatePlaceInfo(){
        val updateIntent = Intent(context, UpdatePlaceInfoActivity::class.java)
        updateIntent.putExtra("type","시설물")
        startActivity(updateIntent)
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun initObserver() {
        super.initObserver()

        mapViewModel.facilityInfo.observe(this){
            binding.apply {
                item = it

                checkFacilityData(it)

                txtFacilityAddress.text =if(it.detailAddress == "none") it.address else it.address + " " + it.detailAddress

                rvFacilityTargetAdapter.run {
                    replaceAll(it.targetList as ArrayList<String>?)
                    notifyDataSetChanged()
                }

                rvFacilityWarningAdapter.run {
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

        binding.rvFacilityDetailTarget.run {
            adapter = rvFacilityTargetAdapter
            layoutManager = targetRvLayoutManager
        }

        binding.rvFacilityDetailWarning.run {
            adapter = rvFacilityWarningAdapter
            layoutManager = warningRvLayoutManager
        }
    }

    private fun setFacilityDataVisibility(type: String){
        when(type){
            "target" -> {
                binding.rvFacilityDetailTarget.visibility = View.INVISIBLE
                binding.inFacilityTargetNone.root.visibility = View.VISIBLE
            }
            "warning" -> {
                binding.rvFacilityDetailWarning.visibility = View.INVISIBLE
                binding.inFailityDetailWarningNone.root.visibility = View.VISIBLE
            }
        }
    }

    private fun checkFacilityData(data : RequestPlaceFacility){
        if(data.targetList == null || data.targetList.isEmpty()) setFacilityDataVisibility("target")
        if(data.warningList == null || data.warningList.isEmpty()) setFacilityDataVisibility("warning")
    }

}