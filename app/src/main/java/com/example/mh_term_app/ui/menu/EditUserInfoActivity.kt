package com.example.mh_term_app.ui.menu

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityEditUserInfoBinding
import com.example.mh_term_app.ui.sign.up.UserInfoViewModel
import com.example.mh_term_app.utils.extension.errorToast
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.extension.toast

class EditUserInfoActivity : BaseActivity<ActivityEditUserInfoBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_edit_user_info

    private val userInfoViewModel: UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            vm = userInfoViewModel
        }
    }

    override fun initView() {
        super.initView()

        binding.tbUpdateUserInfo.apply {
            title = getString(R.string.title_menu_edit_user_info)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }

        userInfoViewModel.setUserData()
        setUserInfo()
    }

    private fun setUserInfo(){
        when(MHApplication.prefManager.userType){
            getString(R.string.txt_user_type_wheelchair) -> binding.rbUpdateTypeWheelchair.isChecked = true
            getString(R.string.txt_user_type_guardian) -> binding.rbUpdateTypeGuardian.isChecked = true
            getString(R.string.txt_user_type_handicap) -> binding.rbUpdateTypeHandicap.isChecked = true
            getString(R.string.txt_user_type_injured) -> binding.rbUpdateTypeInjured.isChecked = true
            getString(R.string.txt_user_type_old) -> binding.rbUpdateTypeOld.isChecked = true
        }
    }

    override fun initListener() {
        super.initListener()

        getUserType()
    }

    private fun getUserType() {
        binding.rgUpdateUserType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_update_type_wheelchair -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_wheelchair))
                R.id.rb_update_type_guardian -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_guardian))
                R.id.rb_update_type_handicap -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_handicap))
                R.id.rb_update_type_injured -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_injured))
                R.id.rb_update_type_old -> userInfoViewModel.setTypeTxt(getString(R.string.txt_user_type_old))
            }
        }
    }

    override fun initObserver() {
        super.initObserver()

        checkUpdateTxt(userInfoViewModel.nicknameTxt)
        checkUpdateTxt(userInfoViewModel.typeTxt)
        checkUpdateBtn(userInfoViewModel.isValidNickname)
        checkUpdateBtn(userInfoViewModel.isValidNickCheckBtn)

        userInfoViewModel.isValidUpdateInfo.observe(this){
            if(it){
                toast("정보 수정 성공")
                MHApplication.prefManager.userNickname = userInfoViewModel.nicknameTxt.value.toString()
                MHApplication.prefManager.userType = if(userInfoViewModel.typeTxt.value.toString()== "null"){"none"} else {userInfoViewModel.typeTxt.value.toString()}
                finish()
            }else{
                errorToast()
            }
        }
    }

    private fun checkUpdateTxt(data : LiveData<String>){
        data.observe(this){
            userInfoViewModel.checkUpdateBtn()
        }
    }

    private fun checkUpdateBtn(data : LiveData<Boolean>){
        data.observe(this){
            userInfoViewModel.checkUpdateBtn()
        }
    }
}