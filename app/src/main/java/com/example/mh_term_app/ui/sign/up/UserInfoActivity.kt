package com.example.mh_term_app.ui.sign.up

import android.os.Bundle
import androidx.activity.viewModels
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityUserInfoBinding
import com.example.mh_term_app.utils.extension.startActivityWithAffinity
import com.example.mh_term_app.utils.extension.toast

class UserInfoActivity : BaseActivity<ActivityUserInfoBinding>() {
    override val layoutResID: Int = R.layout.activity_user_info
    private val userInfoViewModel: UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.apply {
            vm = userInfoViewModel
        }

        initObserver()
        initListener()
    }

    private fun initObserver() {
        userInfoViewModel.isValidSignUp.observe(this
        ) {
            if (it) {
                toast("회원가입 성공")
                startActivityWithAffinity(MainActivity::class.java)
            } else {
                toast("회원가입 실패")
            }
        }
    }

    private fun initListener() {
        getUserType()
    }

    private fun getUserType() {
        viewDataBinding.rgUserType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_type_wheelchair -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_wheelchair))
                R.id.rb_type_guardian -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_guardian))
                R.id.rb_type_handicap -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_handicap))
                R.id.rb_type_injured -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_injured))
                R.id.rb_type_old -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_old))
            }
        }
    }

}