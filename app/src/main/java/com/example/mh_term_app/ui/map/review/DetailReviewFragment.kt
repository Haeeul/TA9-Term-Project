package com.example.mh_term_app.ui.map.review

import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.data.model.response.DetailReviewItem
import com.example.mh_term_app.databinding.FragmentDetailReviewBinding
import com.example.mh_term_app.utils.view.DetailReviewItemDecorator

class DetailReviewFragment : BaseFragment<FragmentDetailReviewBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_detail_review

    lateinit var reviewAdapter: DetailReviewAdapter

    override fun initView() {
        super.initView()

        initReview()
    }

    private fun initReview(){
        reviewAdapter = DetailReviewAdapter()
        reviewAdapter.data = mutableListOf(
            DetailReviewItem(
                "유저1",
                "휠체어 사용자",
                "경사로가 설치되어 있어 휠체어 사용이 편리.\n사장님이 친절하셔서 들어갈 때 도와주셨어요.",
                4.0f,
                false,
                12
            ),
            DetailReviewItem(
                "유저2",
                "영유아 보호자",
                "경사로 덕분에 유모차 쉽게 가지고 들어갔네요~\n다음에 또 방문할게요",
                3.5f,
                true,
                3
            ),
            DetailReviewItem(
                "유저3",
                "시각 장애인",
                "이곳에 유저가 작성한 리뷰의 내용이 들어갑니다.",
                2.5f,
                true,
                0
            )
        )
        binding.rvDetailReview.run {
            adapter = reviewAdapter
            addItemDecoration(DetailReviewItemDecorator(resources.getDimensionPixelSize(R.dimen.rv_detail_review_margin)))
        }

        reviewAdapter.notifyDataSetChanged()
    }
}