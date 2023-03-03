package com.example.mh_term_app.ui.map.review

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.response.ResponseCategoryList
import com.example.mh_term_app.databinding.FragmentDetailReviewBinding
import com.example.mh_term_app.ui.map.details.review.DetailAddReviewActivity
import com.example.mh_term_app.ui.map.details.review.ReviewViewModel
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.view.DetailReviewItemDecorator

class DetailReviewFragment(val item: ResponseCategoryList) : BaseFragment<FragmentDetailReviewBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_detail_review

    private val reviewViewModel : ReviewViewModel by viewModels()
    lateinit var reviewAdapter: DetailReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reviewViewModel.getReviewList(item.id)
    }

    override fun initView() {
        super.initView()

        initReview()
    }

    override fun initObserver() {
        super.initObserver()

    }

    override fun initListener() {
        super.initListener()

        binding.btnReviewAdd.setSingleOnClickListener {
            val reviewIntent = Intent(context, DetailAddReviewActivity::class.java)
            reviewIntent.putExtra("id", item.id)
            reviewIntent.putExtra("type", item.data.detailType)
            reviewIntent.putExtra("name", item.data.name)
            startActivity(reviewIntent)
        }
    }

    private fun initReview(){
        reviewAdapter = DetailReviewAdapter()

        binding.rvDetailReview.run {
            adapter = reviewAdapter
            addItemDecoration(DetailReviewItemDecorator(resources.getDimensionPixelSize(R.dimen.rv_detail_review_margin)))
        }

        reviewAdapter.notifyDataSetChanged()
    }
}