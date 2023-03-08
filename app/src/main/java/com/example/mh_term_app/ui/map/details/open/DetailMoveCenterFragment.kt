package com.example.mh_term_app.ui.map.details.open

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.Time
import com.example.mh_term_app.databinding.FragmentDetailMoveCenterBinding
import com.example.mh_term_app.ui.map.MapViewModel

class DetailMoveCenterFragment(private val centerId : String) : BaseFragment<FragmentDetailMoveCenterBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_detail_move_center

    private val mapViewModel : MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapViewModel.getCenterInfo(centerId)
    }

    @SuppressLint("SetTextI18n")
    override fun initObserver() {
        super.initObserver()

        mapViewModel.centerInfo.observe(viewLifecycleOwner){
            if(it.type.isNotEmpty()){
                binding.apply {
                    item = it

                    txtCenterDetailWeekdayTime.text = setCenterTime(it.reservationTime.weekTime, "weekday") + " 운영"
                    txtCenterDetailHolidayTime.text = setCenterTime(it.reservationTime.holidayTime, "holiday")+ " 운영"
                    txtCenterDetailCarWeekdayTime.text = setCenterTime(it.carRunTime.weekTime, "weekday")+ " 운행"
                    txtCenterDetailCarHolidayTime.text = setCenterTime(it.carRunTime.holidayTime, "holiday")+ " 운행"

                    txtCenterDetailCarInfo.text = "${it.carKind} | ${it.carCount}"

                    txtCenterDetaIlOutsideArea.text = getSplitInfo(it.outsideArea)
                    txtCenterDetailCharge.text = getSplitInfo(it.useCharge)
                    txtCenterDetailTarget.text = getSplitInfo(it.useTarget)
                    txtCenterDetailLimit.text = if(it.limit.isNotEmpty()) getSplitInfo(it.limit) else "없음"
                }
            }else mapViewModel.getCenterInfo(centerId)
        }
    }

    private fun setCenterTime(time : Time, type: String) : String {
        return when(time.openHourTxt){
            "-1" -> "휴무"
            "-2" -> {
                ""
            }
            else -> {
                if(time.openMinuteTxt == "00" || time.openMinuteTxt == "0" && time.closeHourTxt == "23" && time.closeMinuteTxt == "59") "24시간"
                else setTimeFormat(time.openHourTxt) + " : " + setTimeFormat(time.openMinuteTxt) + " ~ " +
                        setTimeFormat(time.closeHourTxt) + " : " + setTimeFormat(time.closeMinuteTxt)
            }
        }
    }

    private fun setTimeFormat(time : String): String {
        return if(time.length == 1) "0$time"
        else time
    }

    private fun getSplitInfo(info : String) : String{
        var str = ""
        val array = info.split("+")

        array.forEach {
            str += "$it\n"
        }

        return str
    }
}