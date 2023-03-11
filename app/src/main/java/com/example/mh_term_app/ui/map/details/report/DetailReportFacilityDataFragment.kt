package com.example.mh_term_app.ui.map.details.report

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.ReportPlaceAddress
import com.example.mh_term_app.data.model.UpdateFacilityInfo
import com.example.mh_term_app.data.model.request.RequestPlaceFacility
import com.example.mh_term_app.databinding.FragmentDetailReportFacilityDataBinding
import com.example.mh_term_app.databinding.ViewPlaceInfoItemNoneBinding
import com.example.mh_term_app.ui.map.MapViewModel
import com.example.mh_term_app.ui.map.details.DetailChipAdapter
import com.example.mh_term_app.ui.map.update.UpdatePlaceInfoActivity
import com.example.mh_term_app.ui.sign.`in`.SignInActivity
import com.example.mh_term_app.utils.extension.createGoToDialog
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

    lateinit var facilityAddressInfo : ReportPlaceAddress
    lateinit var facilityDetailInfo : UpdateFacilityInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapViewModel.getFacilityInfo(facilityId)
        mapViewModel.setLoading(true)
    }

    override fun initView() {
        super.initView()

        binding.vm = mapViewModel

        initRv()
    }

    override fun initListener() {
        super.initListener()

        binding.txtUpdateFacilityInfo.setSingleOnClickListener {
            checkUserListener()
        }

        binding.btnUpdateFacilityInfo.setSingleOnClickListener {
            checkUserListener()
        }

        setNoneItemListener(binding.inFacilityTargetNone, getString(R.string.desc_add_target_info))
        setNoneItemListener(binding.inFailityDetailWarningNone, getString(R.string.desc_add_warning_info))
    }

    private fun setNoneItemListener(view : ViewPlaceInfoItemNoneBinding, content : String){
        view.btnAddInfo.setSingleOnClickListener {
            checkUserListener()
        }

        view.txtInfoItemNoneContent.text = content
    }

    private fun checkUserListener(){
        if(MHApplication.prefManager.haveAccount()) goToUpdatePlaceInfo()
        else{
            requireContext().createGoToDialog(childFragmentManager, "login",
                {
                    val loginIntent = Intent(context, SignInActivity::class.java)
                    loginIntent.putExtra("type", "login")
                    startActivity(loginIntent)
                }, {})
        }
    }

    private fun goToUpdatePlaceInfo(){
        val updateIntent = Intent(context, UpdatePlaceInfoActivity::class.java)
        updateIntent.putExtra("id",facilityId)
        updateIntent.putExtra("placeAddressInfo", facilityAddressInfo)
        updateIntent.putExtra("facilityDetailInfo", facilityDetailInfo)
        startActivity(updateIntent)
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun initObserver() {
        super.initObserver()

        mapViewModel.facilityInfo.observe(viewLifecycleOwner){
            if(it.latitude != 0.0){
                binding.apply {
                    item = it

                    facilityAddressInfo = ReportPlaceAddress(
                        it.type,
                        it.address,
                        it.detailAddress,
                        it.latitude,
                        it.longitude
                    )

                    facilityDetailInfo = UpdateFacilityInfo(
                        it.location,
                        it.detailType,
                        it.targetList ?: mutableListOf(),
                        it.warningList ?: mutableListOf(),
                        it.plusInfo
                    )

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

                    mapViewModel.setLoading(false)
                }
            }else mapViewModel.getFacilityInfo(facilityId)
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