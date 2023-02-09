package com.example.mh_term_app.ui.sign.up

import android.os.Bundle
import androidx.activity.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityUserInfoBinding

class UserInfoActivity : BaseActivity<ActivityUserInfoBinding>() {
    override val layoutResID: Int = R.layout.activity_user_info
    private val userInfoViewModel : UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.apply {
            vm = userInfoViewModel
        }
    }

}