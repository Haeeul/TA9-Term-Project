package com.example.mh_term_app.ui.menu.review

import android.os.Bundle
import androidx.activity.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityUserReviewBinding
import com.example.mh_term_app.ui.map.review.ReviewViewModel
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.view.DetailReviewItemDecorator

class UserReviewActivity : BaseActivity<ActivityUserReviewBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_user_review
    
    private val reviewViewModel : ReviewViewModel by viewModels()
    lateinit var userReviewAdapter: UserReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            vm = reviewViewModel
        }

        reviewViewModel.getUserReview()
    }

    override fun initView() {
        super.initView()

        binding.tbUserReview.apply {
            title = getString(R.string.title_user_review)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }

        initReview()
    }

    private fun initReview(){
        userReviewAdapter = UserReviewAdapter()

        binding.rvUserReview.run {
            adapter = userReviewAdapter
            addItemDecoration(DetailReviewItemDecorator(resources.getDimensionPixelSize(R.dimen.rv_detail_review_margin)))
        }

        userReviewAdapter.notifyDataSetChanged()
    }
}