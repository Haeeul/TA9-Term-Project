package com.example.mh_term_app.ui.map.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mh_term_app.MHApplication
import com.example.mh_term_app.R
import com.example.mh_term_app.data.local.entity.RecentSearch
import com.example.mh_term_app.data.model.response.ResponseReviewList
import com.example.mh_term_app.databinding.RvItemDetailReviewBinding
import com.example.mh_term_app.databinding.RvItemRecentSearchBinding
import com.example.mh_term_app.ui.map.details.review.DetailReviewAdapter
import com.example.mh_term_app.utils.databinding.BindingAdapter.setReviewIcon
import com.example.mh_term_app.utils.extension.setSingleOnClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RecentSearchAdapter(val vm : SearchViewModel) : RecyclerView.Adapter<RecentSearchAdapter.RecentSearchViewHolder>() {
    var data = mutableListOf<RecentSearch>()

    fun replaceAll(array: ArrayList<RecentSearch>?) {
        array?.let {
            data.run {
                clear()
                addAll(it)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentSearchAdapter.RecentSearchViewHolder {
        val binding: RvItemRecentSearchBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_recent_search,
            parent,
            false
        )

        return RecentSearchViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecentSearchAdapter.RecentSearchViewHolder,
        position: Int
    ) {
        data[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class RecentSearchViewHolder(val binding: RvItemRecentSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentSearch) {
            binding.item = item

            binding.btnRecentSearchDelete.setSingleOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    vm.deleteDataRecentSearch(item.placeId)
                }
            }
        }
    }
}