package com.example.mh_term_app.ui.map.update

import android.widget.CheckBox
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.UpdateFacilityInfo
import com.example.mh_term_app.databinding.FragmentUpdateFacilityInfoBinding
import com.example.mh_term_app.ui.menu.report.ReportViewModel
import com.example.mh_term_app.utils.extension.errorToast
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.extension.toast
import com.example.mh_term_app.utils.view.LoadingDialog

class UpdateFacilityInfoFragment(private val facilityId : String, private val facilityDetailInfo: UpdateFacilityInfo) :
    BaseFragment<FragmentUpdateFacilityInfoBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_update_facility_info

    private val reportPlaceViewModel: ReportViewModel by viewModels()
    private lateinit var loadingDialog : LoadingDialog

    private lateinit var detailTypeButtons : List<RadioButton>

    override fun initView() {
        super.initView()

        binding.apply {
            vm = reportPlaceViewModel
        }

        detailTypeButtons = listOf(binding.rbUpdateFacilityBollard,binding.rbUpdateFacilityStreet,binding.rbUpdateFacilityBlock,binding.rbUpdateFacilityCarArea,
        binding.rbUpbdateFacilityCrosswalk,binding.rbUpdateFacilityPass)

        reportPlaceViewModel.setFacilityInfo(facilityDetailInfo)

        checkDetailLocationInfo()
        checkDetailTypeInfo()
        checkTargetInfo()
        checkWarningInfo()

        loadingDialog = LoadingDialog(requireContext())
    }

    private fun checkDetailLocationInfo(){
        when(facilityDetailInfo.location){
            getString(R.string.txt_inside) -> {
                binding.rbUpdateFacilityInside.isChecked = true
            }
            getString(R.string.txt_outside) -> {
                binding.rbUpdateFacilityOutside.isChecked = true
            }
        }
    }

    private fun checkDetailTypeInfo(){
        when(facilityDetailInfo.detailType){
            getString(R.string.txt_bollard) -> {
                binding.rbUpdateFacilityBollard.isChecked = true
            }
            getString(R.string.txt_street) -> {
                binding.rbUpdateFacilityStreet.isChecked = true
            }
            getString(R.string.txt_block) -> {
                binding.rbUpdateFacilityBlock.isChecked = true
            }
            getString(R.string.txt_car_area) -> {
                binding.rbUpdateFacilityCarArea.isChecked = true
            }
            getString(R.string.txt_crosswalk) -> {
                binding.rbUpbdateFacilityCrosswalk.isChecked = true
            }
            getString(R.string.txt_pass) -> {
                binding.rbUpdateFacilityPass.isChecked = true

            }
        }
    }

    private fun checkTargetInfo(){
        facilityDetailInfo.targetList?.forEach {
            when(it){
                getString(R.string.txt_user_type_wheelchair) -> binding.cbUpdateFacilityTargetWheelchair.isChecked = true
                getString(R.string.txt_user_type_guardian) -> binding.cbUpdateFacilityTargetGuardian.isChecked = true
                getString(R.string.txt_user_type_handicap) -> binding.cbUpdateFacilityTargetHandicap.isChecked = true
                getString(R.string.txt_user_type_injured) -> binding.cbUpdateFacilityTargetInjured.isChecked = true
                getString(R.string.txt_user_type_old) -> binding.cbUpdateFacilityTargetOld.isChecked = true
            }
        }
    }

    private fun checkWarningInfo(){
        facilityDetailInfo.warningList?.forEach {
            when(it){
                getString(R.string.txt_warning_slope) -> binding.cbUpdateFacilityWarningSlope.isChecked = true
                getString(R.string.txt_warning_height) -> binding.cbUpdateFacilityWarningHeight.isChecked = true
                getString(R.string.txt_warning_offset) -> binding.cbUpdateFacilityWarningOffset.isChecked = true
                getString(R.string.txt_warning_out) -> binding.cbUpdateFacilityWarningOut.isChecked = true
                getString(R.string.txt_warning_finish) -> binding.cbUpdateFacilityWarningFinish.isChecked = true
                getString(R.string.txt_warning_info) -> binding.cbUpdateFacilityWarningInfo.isChecked = true
                getString(R.string.txt_warning_width) -> binding.cbUpdateFacilityWarningWidth.isChecked = true
            }
        }
    }

    override fun initObserver() {
        super.initObserver()

        checkUpdateTxt(reportPlaceViewModel.locationTxt)
        checkUpdateTxt(reportPlaceViewModel.detailTypeTxt)
        checkUpdateTxt(reportPlaceViewModel.plusInfoTxt)

        checkUpdateList(reportPlaceViewModel.targetList)
        checkUpdateList(reportPlaceViewModel.warningList)

        reportPlaceViewModel.isValidUpdateFacility.observe(this){
            loadingDialog.dismiss()
            if(it) requireContext().toast("제안 완료")
            else requireContext().errorToast()

            val activity = activity as UpdatePlaceInfoActivity
            activity.goToBack()
        }
    }

    private fun checkUpdateTxt(data : LiveData<String>){
        data.observe(this){
            reportPlaceViewModel.checkFacilityInfoUpdateBtn()
        }
    }

    private fun checkUpdateList(data: MutableLiveData<MutableList<String>?>){
        data.observe(this){
            reportPlaceViewModel.checkFacilityInfoUpdateBtn()
        }
    }

    override fun initListener() {
        super.initListener()

        getFacilityDetailLocation()

        setRadioBtnListener(binding.rbUpdateFacilityBollard)
        setRadioBtnListener(binding.rbUpdateFacilityStreet)
        setRadioBtnListener(binding.rbUpdateFacilityBlock)
        setRadioBtnListener(binding.rbUpdateFacilityCarArea)
        setRadioBtnListener(binding.rbUpbdateFacilityCrosswalk)
        setRadioBtnListener(binding.rbUpdateFacilityPass)

        onUpdateFacilityTargetClicked(binding.cbUpdateFacilityTargetWheelchair)
        onUpdateFacilityTargetClicked(binding.cbUpdateFacilityTargetGuardian)
        onUpdateFacilityTargetClicked(binding.cbUpdateFacilityTargetHandicap)
        onUpdateFacilityTargetClicked(binding.cbUpdateFacilityTargetInjured)
        onUpdateFacilityTargetClicked(binding.cbUpdateFacilityTargetOld)

        onUpdateFacilityWarningClicked(binding.cbUpdateFacilityWarningSlope)
        onUpdateFacilityWarningClicked(binding.cbUpdateFacilityWarningHeight)
        onUpdateFacilityWarningClicked(binding.cbUpdateFacilityWarningOffset)
        onUpdateFacilityWarningClicked(binding.cbUpdateFacilityWarningOut)
        onUpdateFacilityWarningClicked(binding.cbUpdateFacilityWarningFinish)
        onUpdateFacilityWarningClicked(binding.cbUpdateFacilityWarningInfo)
        onUpdateFacilityWarningClicked(binding.cbUpdateFacilityWarningWidth)

        binding.btnUpdateInfoFacilityComplete.setSingleOnClickListener {
            loadingDialog.show()
            reportPlaceViewModel.postUpdateFacilityInfo(facilityId)
        }
    }

    private fun getFacilityDetailLocation() {
        binding.rgUpdateLocationFacility.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_update_facility_inside -> reportPlaceViewModel.locationTxt.value = (getString(R.string.txt_inside))
                R.id.rb_update_facility_outside -> reportPlaceViewModel.locationTxt.value = (getString(R.string.txt_outside))
            }
        }
    }

    private fun setRadioBtnListener(button : RadioButton){
        button.setSingleOnClickListener { button
            detailTypeButtons.forEach {
                it.isChecked = it.id == button.id
            }
            reportPlaceViewModel.setDetailTypeTxt(button.text.toString())
        }
    }

    private fun onUpdateFacilityTargetClicked(checkBox: CheckBox) {
        checkBox.setSingleOnClickListener {
            val checked: Boolean = checkBox.isChecked

            reportPlaceViewModel.clickTargetBtn(checked, checkBox.text.toString())
        }

    }

    private fun onUpdateFacilityWarningClicked(checkBox: CheckBox) {
        checkBox.setSingleOnClickListener {
            val checked: Boolean = checkBox.isChecked

            reportPlaceViewModel.clickWarningBtn(checked, checkBox.text.toString())
        }
    }

//    private fun onUpdateFacilityBackBtn(){
//        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                goToBackListener()
//            }
//        })
//    }
//
//    fun goToBackListener(){
//        val activity = activity as UpdatePlaceInfoActivity
//
//        if(reportPlaceViewModel.isValidSendBtn.value == true)
//            requireContext().createGoToDialog(childFragmentManager, "update",
//                {
//                    activity.goToBack()
//                },
//                {}
//            )
//        else activity.goToBack()
//    }

}