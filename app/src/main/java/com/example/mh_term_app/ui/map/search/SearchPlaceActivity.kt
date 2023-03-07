package com.example.mh_term_app.ui.map.search

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivitySearchPlaceBinding
import com.example.mh_term_app.ui.map.NaverMapFragment
import com.example.mh_term_app.utils.extension.setSingleOnClickListener

class SearchPlaceActivity : BaseActivity<ActivitySearchPlaceBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_search_place

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = searchViewModel
    }

    override fun initView() {
        super.initView()

        binding.edtSearchContent.requestFocus()
    }

    override fun initObserver() {
        super.initObserver()

        searchViewModel.placeInfo.observe(this){
            if(it.id.isEmpty()) searchViewModel.getSearchAddress()
            else{
                val searchResultIntent = Intent(this, NaverMapFragment::class.java)
                searchResultIntent.putExtra("searchResult",it)
                setResult(RESULT_OK,searchResultIntent)
                finish()
            }
        }

        searchViewModel.coordInfo.observe(this){
            val searchResultIntent = Intent(this, NaverMapFragment::class.java)
            searchResultIntent.putExtra("latitude",it.latitude)
            searchResultIntent.putExtra("longitude",it.longitude)
            setResult(100,searchResultIntent)
            finish()
        }
    }

    override fun initListener() {
        super.initListener()

        binding.btnSearchBack.setSingleOnClickListener {
            finish()
        }

        binding.edtSearchContent.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchViewModel.getSearchName()
                handled = true
            }
            handled
        }

    }
}