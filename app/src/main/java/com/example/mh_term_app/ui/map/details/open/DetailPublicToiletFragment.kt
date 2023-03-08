package com.example.mh_term_app.ui.map.details.open

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mh_term_app.R
import com.example.mh_term_app.base.BaseFragment
import com.example.mh_term_app.databinding.FragmentDetailPublicToiletBinding

class DetailPublicToiletFragment(private val toiletId : String) : BaseFragment<FragmentDetailPublicToiletBinding>() {
    override val layoutResID: Int
        get() = R.layout.fragment_detail_public_toilet



}