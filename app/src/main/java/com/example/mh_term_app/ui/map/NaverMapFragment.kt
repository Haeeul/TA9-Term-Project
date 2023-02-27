package com.example.mh_term_app.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.MainActivity
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.databinding.FragmentNaverMapBinding
import com.example.mh_term_app.ui.menu.EditUserInfoActivity
import com.example.mh_term_app.ui.menu.UserFavotireActivity
import com.example.mh_term_app.ui.menu.UserReviewActivity
import com.example.mh_term_app.ui.menu.report.ReportPlaceActivity
import com.example.mh_term_app.ui.sign.`in`.SignInActivity
import com.example.mh_term_app.ui.sign.up.SignUpActivity
import com.example.mh_term_app.utils.databinding.BindingAdapter.setUserTypeChip
import com.example.mh_term_app.utils.extension.intent
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.extension.toast
import com.google.android.material.chip.Chip

class NaverMapFragment : BaseFragment<FragmentNaverMapBinding>(){
    override val layoutResID
        get() = R.layout.fragment_naver_map
    private lateinit var callback: OnBackPressedCallback
    private val mapViewModel : MapViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        binding.drawerLayout.closeDrawers()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                    binding.drawerLayout.closeDrawers()
                }else{
                    // 백스택 구현
                    super.remove()
                    (activity as MainActivity).onBackPressed()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun initObserver() {
        super.initObserver()

        mapViewModel.categoryList.observe(this){
            val activity = activity as MainActivity
            activity.setCategoryMarkerList(it)
        }
    }

    override fun initView() {
        super.initView()

        initDrawer()
    }

    override fun initListener() {
        super.initListener()

        binding.edtMapSearch.setOnClickListener {
            val activity = activity as MainActivity
            activity.goToSearchListener()
            activity.setInfoWindowVisibility(false)
        }

        binding.chipFacility.setOnClickListener {
            mapViewModel.getCategoryList("시설물")
        }

        binding.chipStore.setOnClickListener {
            mapViewModel.getCategoryList("매장")
        }

        binding.chipChargingStation.setOnClickListener {
            context?.toast("장소 준비중")
        }

        binding.chipRestroom.setOnClickListener {
            context?.toast("장소 준비중")
        }

        binding.chipSupportCenter.setOnClickListener {
            context?.toast("장소 준비중")
        }
    }

    private fun initDrawer() {
        binding.btnMapMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
            val activity = activity as MainActivity
            activity.setInfoWindowVisibility(false)
        }

        inflateMenu()
        setDrawerListener()
        clickBtnCancel()
    }

    @SuppressLint("SetTextI18n")
    private fun inflateMenu(){
        binding.nvDrawerMenu.removeHeaderView(binding.nvDrawerMenu.getHeaderView(0))

        if(MHApplication.prefManager.haveAccount()) {
            binding.nvDrawerMenu.inflateMenu(R.menu.nv_drawer_menu_user)
            binding.nvDrawerMenu.inflateHeaderView(R.layout.nv_drawer_header_user)
            binding.nvDrawerMenu.getHeaderView(0).findViewById<TextView>(R.id.txt_menu_user_name).text = MHApplication.prefManager.userNickname+" 님"
            binding.nvDrawerMenu.getHeaderView(0).findViewById<Chip>(R.id.chip_menu_user_type).apply {
                if(MHApplication.prefManager.userType != "none"){
                    this.setUserTypeChip(MHApplication.prefManager.userType)
                    visibility = View.VISIBLE
                }else{
                    visibility = View.GONE
                }
            }

        }else{
            binding.nvDrawerMenu.menu.removeItem(R.id.edit_user_info)
            binding.nvDrawerMenu.menu.removeItem(R.id.review_list)
            binding.nvDrawerMenu.menu.removeItem(R.id.favorite_list)
            binding.nvDrawerMenu.menu.removeItem(R.id.report)
            binding.nvDrawerMenu.menu.removeItem(R.id.logout)

            binding.nvDrawerMenu.inflateMenu(R.menu.nv_drawer_menu_none)
            binding.nvDrawerMenu.inflateHeaderView(R.layout.nv_drawer_header_none)
        }
    }

    private fun setDrawerListener(){
        binding.nvDrawerMenu.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.edit_user_info -> intent(EditUserInfoActivity::class.java)
                R.id.review_list -> intent(UserReviewActivity::class.java)
                R.id.favorite_list -> intent(UserFavotireActivity::class.java)
                R.id.report -> intent(ReportPlaceActivity::class.java)
                R.id.go_to_sign_in -> intent(SignInActivity::class.java)
                R.id.go_to_sign_up -> intent(SignUpActivity::class.java)
                else -> {
                    MHApplication.prefManager.clear()
                    context?.toast("로그아웃되었습니다.")
                    inflateMenu()
                    binding.drawerLayout.closeDrawers()
                }
            }
            true
        }
    }

    private fun clickBtnCancel(){
        binding.nvDrawerMenu.getHeaderView(0).findViewById<ImageView>(R.id.btn_menu_close).setSingleOnClickListener {
            binding.drawerLayout.closeDrawers()
        }
    }
}