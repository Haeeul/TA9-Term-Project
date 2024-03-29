package com.example.mh_term_app.ui.map.details.review

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.response.ResponseCategoryPlace
import com.example.mh_term_app.databinding.FragmentDetailReviewBinding
import com.example.mh_term_app.ui.map.review.DetailAddReviewActivity
import com.example.mh_term_app.ui.map.review.ReviewViewModel
import com.example.mh_term_app.ui.sign.`in`.SignInActivity
import com.example.mh_term_app.utils.extension.createGoToDialog
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import com.example.mh_term_app.utils.view.DetailReviewItemDecorator

class DetailReviewFragment(val item: ResponseCategoryPlace) : BaseFragment<FragmentDetailReviewBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_detail_review

    private val reviewViewModel : ReviewViewModel by activityViewModels()
    lateinit var reviewAdapter: DetailReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reviewViewModel.getReviewList(item.id)
    }

    override fun initView() {
        super.initView()

        binding.vm = reviewViewModel
        initReview()
    }

    override fun initObserver() {
        super.initObserver()

    }

    override fun onResume() {
        super.onResume()

        reviewViewModel.getReviewList(item.id)
    }

    override fun initListener() {
        super.initListener()

        goToAddReview(binding.btnReviewAdd)
        goToAddReview(binding.btnReviewNoneAdd)
    }

    private fun goToAddReview(textView: TextView){
        textView.setSingleOnClickListener {
            if(MHApplication.prefManager.haveAccount()){
                val reviewIntent = Intent(context, DetailAddReviewActivity::class.java)
                reviewIntent.putExtra("id", item.id)

                if(item.data.type == "매장") {
                    reviewIntent.putExtra("type", item.data.detailType)
                    reviewIntent.putExtra("name", item.data.name)
                }else{
                    reviewIntent.putExtra("type", item.data.location)
                    reviewIntent.putExtra("name", item.data.detailType)
                }

                startActivity(reviewIntent)
            }
            else{
                requireContext().createGoToDialog(childFragmentManager, "login",
                    {
                        val loginIntent = Intent(context, SignInActivity::class.java)
                        loginIntent.putExtra("type", "login")
                        startActivity(loginIntent)
                    }, {})
            }
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