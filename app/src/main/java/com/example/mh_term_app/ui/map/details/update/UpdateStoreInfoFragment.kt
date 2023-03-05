package com.example.mh_term_app.ui.map.details.update

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.UpdateStoreInfo
import com.example.mh_term_app.databinding.FragmentUpdateStoreInfoBinding
import com.example.mh_term_app.ui.menu.report.ReportViewModel
import com.example.mh_term_app.utils.extension.createStoreTimeDialog
import com.example.mh_term_app.utils.extension.errorToast
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.extension.toast

class UpdateStoreInfoFragment(private val storeId : String, private val storeDetailInfo: UpdateStoreInfo) :
    BaseFragment<FragmentUpdateStoreInfoBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_update_store_info

    private val reportPlaceViewModel: ReportViewModel by viewModels()

    override fun initView() {
        super.initView()

        binding.apply {
            vm = reportPlaceViewModel
        }

        reportPlaceViewModel.setInfo(storeDetailInfo)

        checkDetailTypeInfo()
        checkTargetInfo()
        checkWarningInfo()

    }

    private fun checkDetailTypeInfo(){
        when(storeDetailInfo.detailType){
            getString(R.string.txt_restaurant) -> {
                binding.rbUpdateStoreRestaurant.isChecked = true
                binding.edtUpdateStoreEtcType.visibility = View.GONE
            }
            getString(R.string.txt_cafe) -> {
                binding.rbUpdateStoreCafe.isChecked = true
                binding.edtUpdateStoreEtcType.visibility = View.GONE
            }
            else -> {
                binding.rbUpdateStoreEtc.isChecked = true
                binding.edtUpdateStoreEtcType.visibility = View.VISIBLE
            }
        }
    }

    private fun checkTargetInfo(){
        storeDetailInfo.targetList?.forEach {
            when(it){
                getString(R.string.txt_user_type_wheelchair) -> binding.cbUpdateStoreTargetWheelchair.isChecked = true
                getString(R.string.txt_user_type_guardian) -> binding.cbUpdateStoreTargetGuardian.isChecked = true
                getString(R.string.txt_user_type_handicap) -> binding.cbUpdateStoreTargetHandicap.isChecked = true
                getString(R.string.txt_user_type_injured) -> binding.cbUpdateStoreTargetInjured.isChecked = true
                getString(R.string.txt_user_type_old) -> binding.cbUpdateStoreTargetOld.isChecked = true
            }
        }
    }

    private fun checkWarningInfo(){
        storeDetailInfo.warningList?.forEach {
            when(it){
                getString(R.string.txt_warning_slope) -> binding.cbUpdateStoreWarningSlope.isChecked = true
                getString(R.string.txt_warning_height) -> binding.cbUpdateStoreWarningHeight.isChecked = true
                getString(R.string.txt_warning_offset) -> binding.cbUpdateStoreWarningOffset.isChecked = true
                getString(R.string.txt_warning_out) -> binding.cbUpdateStoreWarningOut.isChecked = true
                getString(R.string.txt_warning_finish) -> binding.cbUpdateStoreWarningFinish.isChecked = true
                getString(R.string.txt_warning_info) -> binding.cbUpdateStoreWarningInfo.isChecked = true
                getString(R.string.txt_warning_width) -> binding.cbUpdateStoreWarningWidth.isChecked = true
            }
        }
    }

    override fun initObserver() {
        super.initObserver()

        checkUpdateTxt(reportPlaceViewModel.storeNameTxt)
        checkUpdateTxt(reportPlaceViewModel.storePhoneTxt)
        checkUpdateTxt(reportPlaceViewModel.storeWeekTimeTxt)
        checkUpdateTxt(reportPlaceViewModel.storeSaturdayTimeTxt)
        checkUpdateTxt(reportPlaceViewModel.storeMondayTimeTxt)
        checkUpdateTxt(reportPlaceViewModel.detailTypeTxt)
        checkUpdateTxt(reportPlaceViewModel.plusInfoTxt)

        checkUpdateList(reportPlaceViewModel.targetList)
        checkUpdateList(reportPlaceViewModel.warningList)

        reportPlaceViewModel.etcTypeTxt.observe(this){
            if(binding.rbUpdateStoreEtc.isChecked) reportPlaceViewModel.setDetailTypeTxt(it)
        }

        reportPlaceViewModel.isValidUpdateStore.observe(this){
            if(it) requireContext().toast("제안 완료")
            else requireContext().errorToast()

            val activity = activity as UpdatePlaceInfoActivity
            activity.goToBack()
        }
    }

    private fun checkUpdateTxt(data : LiveData<String>){
        data.observe(this){
            reportPlaceViewModel.checkStoreInfoUpdateBtn()
        }
    }

    private fun checkUpdateList(data: MutableLiveData<MutableList<String>?>){
        data.observe(this){
            reportPlaceViewModel.checkStoreInfoUpdateBtn()
        }
    }

    override fun initListener() {
        super.initListener()

        onClickTimeListener("week", binding.txtUpdateInfoStoreWeekTime)
        onClickTimeListener("saturday", binding.txtUpdateInfoStoreSaturdayTime)
        onClickTimeListener("monday", binding.txtUpdateInfoStoreMondayTime)

        getStoreDetailType()

        onUpdateStoreTargetClicked(binding.cbUpdateStoreTargetWheelchair)
        onUpdateStoreTargetClicked(binding.cbUpdateStoreTargetGuardian)
        onUpdateStoreTargetClicked(binding.cbUpdateStoreTargetHandicap)
        onUpdateStoreTargetClicked(binding.cbUpdateStoreTargetInjured)
        onUpdateStoreTargetClicked(binding.cbUpdateStoreTargetOld)

        onUpdateStoreWarningClicked(binding.cbUpdateStoreWarningSlope)
        onUpdateStoreWarningClicked(binding.cbUpdateStoreWarningHeight)
        onUpdateStoreWarningClicked(binding.cbUpdateStoreWarningOffset)
        onUpdateStoreWarningClicked(binding.cbUpdateStoreWarningOut)
        onUpdateStoreWarningClicked(binding.cbUpdateStoreWarningFinish)
        onUpdateStoreWarningClicked(binding.cbUpdateStoreWarningInfo)
        onUpdateStoreWarningClicked(binding.cbUpdateStoreWarningWidth)

        binding.btnUpdateInfoStoreComplete.setSingleOnClickListener {
            reportPlaceViewModel.postUpdateStoreInfo(storeId)
        }
    }

    private fun onClickTimeListener(type: String, txt: TextView) {
        txt.setSingleOnClickListener {
            requireContext().createStoreTimeDialog(childFragmentManager, type, reportPlaceViewModel)
        }
    }

    private fun getStoreDetailType() {
        binding.rgUpdateInfoStoreType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_update_store_restaurant -> {
                    reportPlaceViewModel.setDetailTypeTxt(getString(R.string.txt_restaurant))
                    binding.edtUpdateStoreEtcType.visibility = View.GONE
                }
                R.id.rb_update_store_cafe -> {
                    reportPlaceViewModel.setDetailTypeTxt(getString(R.string.txt_cafe))
                    binding.edtUpdateStoreEtcType.visibility = View.GONE
                }
                R.id.rb_update_store_etc -> {
                    reportPlaceViewModel.setDetailTypeTxt(reportPlaceViewModel.etcTypeTxt.value.toString())
                    binding.edtUpdateStoreEtcType.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onUpdateStoreTargetClicked(checkBox: CheckBox) {
        checkBox.setSingleOnClickListener {
            val checked: Boolean = checkBox.isChecked

            reportPlaceViewModel.clickTargetBtn(checked, checkBox.text.toString())
        }

    }

    private fun onUpdateStoreWarningClicked(checkBox: CheckBox) {
        checkBox.setSingleOnClickListener {
            val checked: Boolean = checkBox.isChecked

            reportPlaceViewModel.clickWarningBtn(checked, checkBox.text.toString())
        }

    }
}