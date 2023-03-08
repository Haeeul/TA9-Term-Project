package com.example.mh_term_app.ui.menu

import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityUserFavoriteBinding
import com.example.mh_term_app.utils.extension.setSingleOnClickListener

class UserFavoriteActivity : BaseActivity<ActivityUserFavoriteBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_user_favorite

    override fun initView() {
        super.initView()

        binding.tbUserFavorite.apply {
            title = getString(R.string.title_menu_favorite_list)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }
    }
}