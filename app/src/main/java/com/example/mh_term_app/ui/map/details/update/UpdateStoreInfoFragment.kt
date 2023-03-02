package com.example.mh_term_app.ui.map.details.update

import android.util.Log
import androidx.fragment.app.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.Time
import com.example.mh_term_app.data.model.UpdateStoreInfo
import com.example.mh_term_app.databinding.FragmentUpdateStoreInfoBinding
import com.example.mh_term_app.ui.menu.report.ReportViewModel

class UpdateStoreInfoFragment(private val storeDetailInfo : UpdateStoreInfo) : BaseFragment<FragmentUpdateStoreInfoBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_update_store_info


}