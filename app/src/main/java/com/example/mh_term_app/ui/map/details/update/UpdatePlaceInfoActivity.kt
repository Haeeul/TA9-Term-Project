package com.example.mh_term_app.ui.map.details.update

import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityUpdatePlaceInfoBinding
import com.example.mh_term_app.utils.extension.setSingleOnClickListener

class UpdatePlaceInfoActivity : BaseActivity<ActivityUpdatePlaceInfoBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_update_place_info

    override fun initView() {
        super.initView()

        binding.tbUptaePlaceInfo.apply {
            title = MHApplication.getApplicationContext().getString(R.string.title_update_place_info)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }
    }
}