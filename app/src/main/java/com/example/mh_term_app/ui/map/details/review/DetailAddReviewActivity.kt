package com.example.mh_term_app.ui.map.details.review

import androidx.activity.viewModels
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityDetailAddReviewBinding
import com.example.mh_term_app.utils.extension.setSingleOnClickListener

class DetailAddReviewActivity : BaseActivity<ActivityDetailAddReviewBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_detail_add_review

    private val reviewViewModel: ReviewViewModel by viewModels()

    override fun initView() {
        super.initView()

        binding.tbAddReview.apply {
            title = getString(R.string.title_review_add)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }

        binding.apply {
            vm = reviewViewModel
            txtAddReviewPlaceType.text = intent.getStringExtra("type")
            txtAddReviewPlaceName.text = intent.getStringExtra("name")
        }

    }

    override fun initObserver() {
        super.initObserver()

        reviewViewModel.reviewTxt.observe(this){
            reviewViewModel.checkCompleteBtn()
        }

        reviewViewModel.rating.observe(this){
            reviewViewModel.checkCompleteBtn()
        }
    }
}