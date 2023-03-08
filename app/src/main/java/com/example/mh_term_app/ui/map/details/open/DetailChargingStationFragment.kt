package com.example.mh_term_app.ui.map.details.open

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.Time
import com.example.mh_term_app.databinding.FragmentDetailChargingStationBinding
import com.example.mh_term_app.ui.map.MapViewModel
import com.example.mh_term_app.utils.databinding.BindingAdapter.setBooleanTxt

class DetailChargingStationFragment(private val chargingId : String) : BaseFragment<FragmentDetailChargingStationBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_detail_charging_station

    private val mapViewModel : MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapViewModel.getChargingInfo(chargingId)
    }

    @SuppressLint("SetTextI18n")
    override fun initObserver() {
        super.initObserver()

        mapViewModel.chargingInfo.observe(viewLifecycleOwner){
            if(it.type.isNotEmpty()){
                binding.apply {
                    item = it

                    txtChargingDetailAir.setBooleanTxt(it.airUse)
                    txtChargingDetailPhoneUse.setBooleanTxt(it.phoneUse)
                    txtChargingDetailSame.text = "${it.sameUse}대"

                    txtChargingDetailWeekdayTime.text = setChargingTime(it.time.weekTime, "weekday")
                    txtChargingDetailSaturdayTime.text = setChargingTime(it.time.saturdayTime, "saturday")
                    txtChargingDetailMondayTime.text = setChargingTime(it.time.mondayTime, "monday")
                }
            }else mapViewModel.getChargingInfo(chargingId)
        }
    }

    private fun setChargingTime(time : Time, type: String) : String {
        return when(time.openHourTxt){
            "-1" -> "휴무"
            "-2" -> {
                setChargingDataVisibility(type)
                ""
            }
            else -> {
                if(time.openMinuteTxt == "00" || time.openMinuteTxt == "0" && time.closeHourTxt == "23" && time.closeMinuteTxt == "59") "24시간 이용 가능"
                else setTimeFormat(time.openHourTxt) + " : " + setTimeFormat(time.openMinuteTxt) + " ~ " +
                        setTimeFormat(time.closeHourTxt) + " : " + setTimeFormat(time.closeMinuteTxt)
            }
        }
    }

    private fun setTimeFormat(time : String): String {
        return if(time.length == 1) "0$time"
        else time
    }

    private fun setChargingDataVisibility(type: String){
        when(type){
            "weekday" -> binding.inChargingDetailWeekdayNone.root.visibility = View.VISIBLE
            "saturday" -> binding.inChargingDetailSaturdayNone.root.visibility = View.VISIBLE
            "monday" -> binding.inChargingDetailMondayNone.root.visibility = View.VISIBLE
//            "phone" -> {
//                binding.inStoreDetailPhoneNone.root.visibility = View.VISIBLE
//                binding.txtStoreDetailPhone.visibility = View.INVISIBLE
//            }
//            "target" -> {
//                binding.rvStoreDetailTarget.visibility = View.INVISIBLE
//                binding.inStoreDetailTargetNone.root.visibility = View.VISIBLE
//            }
//            "warning" -> {
//                binding.rvStoreDetailWarning.visibility = View.INVISIBLE
//                binding.inStoreDetailWarningNone.root.visibility = View.VISIBLE
//            }
        }
    }
}