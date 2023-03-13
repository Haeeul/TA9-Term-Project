package com.example.mh_term_app.ui.map.details.report

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.OriginStoreInfo
import com.example.mh_term_app.data.model.ReportPlaceAddress
import com.example.mh_term_app.data.model.StoreTime
import com.example.mh_term_app.data.model.Time
import com.example.mh_term_app.data.model.request.RequestPlaceStore
import com.example.mh_term_app.data.model.response.PlaceBasicInfo
import com.example.mh_term_app.databinding.FragmentDetailReportStoreDataBinding
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


class DetailReportStoreDataFragment(private val storeId : String) : BaseFragment<FragmentDetailReportStoreDataBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_detail_report_store_data

    private var rvStoreTargetAdapter = DetailChipAdapter()
    private var rvStoreWarningAdapter = DetailChipAdapter()
    private val mapViewModel : MapViewModel by viewModels()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    lateinit var storeAddressInfo : ReportPlaceAddress
    lateinit var storeDetailInfo : OriginStoreInfo

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapViewModel.getStoreInfo(storeId)
        mapViewModel.setLoading(true)

        setResultUpdate()
    }

    override fun onResume() {
        super.onResume()

        mapViewModel.getStoreInfo(storeId)
        mapViewModel.setLoading(true)
    }

    override fun initView() {
        super.initView()

        binding.vm = mapViewModel

        initRv()
    }

    override fun initListener() {
        super.initListener()

        binding.txtUpdateStoreInfo.setSingleOnClickListener {
            checkUserListener()
        }

        binding.btnUpdateStoreInfo.setSingleOnClickListener {
            checkUserListener()
        }

        setNoneItemListener(binding.inStoreDetailWeekdayNone, getString(R.string.desc_add_time_info))
        setNoneItemListener(binding.inStoreDetailSaturdayNone, getString(R.string.desc_add_time_info))
        setNoneItemListener(binding.inStoreDetailMondayNone, getString(R.string.desc_add_time_info))
        setNoneItemListener(binding.inStoreDetailPhoneNone, getString(R.string.desc_add_phone_info))
        setNoneItemListener(binding.inStoreDetailTargetNone, getString(R.string.desc_add_target_info))
        setNoneItemListener(binding.inStoreDetailWarningNone, getString(R.string.desc_add_warning_info))
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
        updateIntent.putExtra("id",storeId)
        updateIntent.putExtra("placeAddressInfo", storeAddressInfo)
        updateIntent.putExtra("storeDetailInfo", storeDetailInfo)
        resultLauncher.launch(updateIntent)
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun initObserver() {
        super.initObserver()

        mapViewModel.storeInfo.observe(viewLifecycleOwner){
            if(it.latitude != 0.0) {
                binding.apply {
                    item = it

                    storeAddressInfo = ReportPlaceAddress(
                        it.type,
                        it.address,
                        it.detailAddress,
                        it.latitude,
                        it.longitude
                    )

                    storeDetailInfo = OriginStoreInfo(
                        it.name,
                        if (it.phone == "none") "" else it.phone,
                        changeToListValue(it.time),
                        it.detailType,
                        it.targetList ?: mutableListOf(),
                        it.warningList ?: mutableListOf(),
                        it.plusInfo
                    )

                    checkStoreData(it)

                    txtStoreAddress.text =
                        if (it.detailAddress == "none") it.address else it.address + " " + it.detailAddress

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

                    mapViewModel.setLoading(false)
                }
            }else mapViewModel.getStoreInfo(storeId)
        }
    }

    private fun changeToListValue(time : StoreTime):MutableList<MutableList<String>>{
        return mutableListOf(
            mutableListOf(time.weekTime.openHourTxt,time.weekTime.openMinuteTxt,time.weekTime.closeHourTxt,time.weekTime.closeMinuteTxt),
            mutableListOf(time.saturdayTime.openHourTxt,time.saturdayTime.openMinuteTxt,time.saturdayTime.closeHourTxt,time.saturdayTime.closeMinuteTxt),
            mutableListOf(time.mondayTime.openHourTxt,time.mondayTime.openMinuteTxt,time.mondayTime.closeHourTxt,time.mondayTime.closeMinuteTxt))
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
                setStoreDataVisibility(type)
                ""
            }
            else -> {
                if(time.openMinuteTxt == "0" && time.closeHourTxt == "23" && time.closeMinuteTxt == "59") "24시간 영업"
                else setTimeFormat(time.openHourTxt) + " : " + setTimeFormat(time.openMinuteTxt) + " ~ " +
                        setTimeFormat(time.closeHourTxt) + " : " + setTimeFormat(time.closeMinuteTxt)
            }
        }
    }

    private fun setTimeFormat(time : String): String {
        return if(time.length == 1) "0$time"
        else time
    }

    private fun setStoreDataVisibility(type: String){
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

    private fun checkStoreData(data : RequestPlaceStore){
        if(data.phone == "none") setStoreDataVisibility("phone")
        if(data.targetList == null || data.targetList.isEmpty()) setStoreDataVisibility("target")
        if(data.warningList == null || data.warningList.isEmpty()) setStoreDataVisibility("warning")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setResultUpdate(){
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data?.getParcelableExtra<PlaceBasicInfo>("updateResult")!!

                val activity = activity as MainActivity
                activity.setStoreBasicData(data)
            }
        }
    }
}