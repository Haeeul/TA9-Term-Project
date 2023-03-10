package com.example.mh_term_app.ui.sign.up

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityUserInfoBinding
import com.example.mh_term_app.utils.extension.*

class UserInfoActivity : BaseActivity<ActivityUserInfoBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_user_info
    private val userInfoViewModel: UserInfoViewModel by viewModels()

    var id = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            vm = userInfoViewModel
            edtUserInfoNick.requestFocus()
        }

        id = intent.getStringExtra("id").toString()
        password = intent.getStringExtra("password").toString()
    }

    override fun initView() {
        super.initView()

        binding.tbUserInfo.apply {
            title = getString(R.string.title_sign_up)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }
    }

    override fun initObserver() {
        setKeyboardObserver(userInfoViewModel.isValidNickname, false)

        userInfoViewModel.isValidSignUp.observe(this) {
            if (it) {
                MHApplication.prefManager.userId = id
                MHApplication.prefManager.userPassword = password
                MHApplication.prefManager.userNickname = userInfoViewModel.nicknameTxt.value.toString()
                MHApplication.prefManager.userType = if(userInfoViewModel.typeTxt.value.toString()== "null"){"none"} else {userInfoViewModel.typeTxt.value.toString()}
                toast("회원가입 성공")
                startActivityWithAffinity(MainActivity::class.java)
            } else {
                errorToast()
            }
        }
    }

    override fun initListener() {
        super.initListener()

        getUserType()
    }

    private fun getUserType() {
        binding.rgUserType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_type_wheelchair -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_wheelchair))
                R.id.rb_type_guardian -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_guardian))
                R.id.rb_type_handicap -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_handicap))
                R.id.rb_type_injured -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_injured))
                R.id.rb_type_old -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_old))
            }
        }
    }

    fun postSignUp(view : View){
        userInfoViewModel.postSignUp(id,password)
    }
}