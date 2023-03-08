package com.example.mh_term_app.ui.map.details

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.databinding.FragmentDetailChargingStationBinding
import com.example.mh_term_app.ui.map.MapViewModel

class DetailChargingStationFragment(private val chargingId : String) : BaseFragment<FragmentDetailChargingStationBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_detail_charging_station

    private val mapViewModel : MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapViewModel.getChargingInfo(chargingId)
    }

}