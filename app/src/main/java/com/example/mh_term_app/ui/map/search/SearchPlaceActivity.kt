package com.example.mh_term_app.ui.map.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.data.local.entity.RecentSearch
import com.example.mh_term_app.databinding.ActivitySearchPlaceBinding
import com.example.mh_term_app.ui.map.NaverMapFragment
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.view.DetailReviewItemDecorator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchPlaceActivity : BaseActivity<ActivitySearchPlaceBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_search_place

    private val searchViewModel: SearchViewModel by viewModels()
    lateinit var recentSearchAdapter: RecentSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = searchViewModel
    }

    override fun initView() {
        super.initView()

        binding.edtSearchContent.requestFocus()
        initRecentSearchRv()
    }

    override fun initObserver() {
        super.initObserver()

        searchViewModel.placeInfo.observe(this){
            val searchResultIntent = Intent(this, NaverMapFragment::class.java)
            searchResultIntent.putExtra("searchResult",it)
            setResult(RESULT_OK,searchResultIntent)
            finish()
        }

        searchViewModel.recentSearchList.observe(this){
            recentSearchAdapter.run {
                replaceAll(it as ArrayList<RecentSearch>?)
            }

            recentSearchAdapter.notifyDataSetChanged()

            if(it.isNotEmpty()) searchViewModel.setValidRecentList(true)
            else searchViewModel.setValidRecentList(false)
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
                searchViewModel.getSearchInfo()
                handled = true
            }
            handled
        }

        binding.btnRecentSearchAllDelete.setSingleOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                searchViewModel.deleteAllRecentSearch()
            }
        }

    }

    private fun initRecentSearchRv(){
        recentSearchAdapter = RecentSearchAdapter(searchViewModel)

        binding.rvRecentSearch.run {
            adapter = recentSearchAdapter
            addItemDecoration(DetailReviewItemDecorator(resources.getDimensionPixelSize(R.dimen.rv_detail_review_margin)))
        }

        recentSearchAdapter.notifyDataSetChanged()
    }
}