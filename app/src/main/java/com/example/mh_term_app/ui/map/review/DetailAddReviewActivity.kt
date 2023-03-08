package com.example.mh_term_app.ui.map.review

import android.os.Build
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseActivity
import com.example.mh_term_app.databinding.ActivityDetailAddReviewBinding
import com.example.mh_term_app.utils.extension.errorToast
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.extension.toast

class DetailAddReviewActivity : BaseActivity<ActivityDetailAddReviewBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_detail_add_review

    private val reviewViewModel: ReviewViewModel by viewModels()

    var placeType = ""
    var placeName = ""
    var placeId = ""

    override fun initView() {
        super.initView()

        placeId = intent.getStringExtra("id").toString()
        placeType = intent.getStringExtra("type").toString()
        placeName = intent.getStringExtra("name").toString()


        binding.tbAddReview.apply {
            title = getString(R.string.title_review_add)
            btnTbBack.setSingleOnClickListener {
                finish()
            }
        }

        binding.apply {
            vm = reviewViewModel
            txtAddReviewPlaceType.text = placeType
            txtAddReviewPlaceName.text = placeName
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

        reviewViewModel.isValidReview.observe(this){
            if(it) toast("작성 완료")
            else errorToast()

            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun postReview(view : View){
        reviewViewModel.postReview(placeId, placeType, placeName)
    }
}