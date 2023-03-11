package com.example.mh_term_app.ui.map.details.open

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.Time
import com.example.mh_term_app.databinding.FragmentDetailPublicToiletBinding
import com.example.mh_term_app.ui.map.MapViewModel
import com.example.mh_term_app.utils.databinding.BindingAdapter.setBooleanTxt
import com.example.mh_term_app.utils.databinding.BindingAdapter.setUnisexTxt

class DetailPublicToiletFragment(private val toiletId : String) : BaseFragment<FragmentDetailPublicToiletBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_detail_public_toilet

    private val mapViewModel : MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapViewModel.getToiletInfo(toiletId)
        mapViewModel.setLoading(true)
    }

    override fun initView() {
        super.initView()

        binding.vm = mapViewModel
    }

    @SuppressLint("SetTextI18n")
    override fun initObserver() {
        super.initObserver()

        mapViewModel.toiletInfo.observe(viewLifecycleOwner){
            if(it.type.isNotEmpty()){
                binding.apply {
                    item = it

                    txtToiletDetailTime.text = setToiletTime(it.time, "weekday")

                    txtToiletDetailUnisex.setUnisexTxt(it.unisex)
                }

                mapViewModel.setLoading(false)
            }else mapViewModel.getToiletInfo(toiletId)
        }
    }

    private fun setToiletTime(time : Time, type: String) : String {
        return when(time.openHourTxt){
            "-1" -> "이용 불가"
            "-2" -> {
                ""
            }
            else -> {
                if(time.openMinuteTxt == "00" || time.openMinuteTxt == "0" && time.closeHourTxt == "23" && time.closeMinuteTxt == "59") "24시간 개방"
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