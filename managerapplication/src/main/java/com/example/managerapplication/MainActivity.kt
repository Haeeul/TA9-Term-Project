package com.example.managerapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.example.managerapplication.base.BaseActivity
import com.example.managerapplication.databinding.ActivityMainBinding
import com.example.managerapplication.utils.extension.setSingleOnClickListener

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResID: Int
        get() = R.layout.activity_main

    private val viewModel : PlaceViewModel by viewModels()

    override fun initListener() {
        super.initListener()

        binding.button.setSingleOnClickListener {
            viewModel.getChargingStationList()
        }
        binding.button2.setSingleOnClickListener {
            viewModel.getMovementList()
        }
        binding.button3.setSingleOnClickListener {
            viewModel.getPublicToiletList()
        }

        binding.button5.setSingleOnClickListener {
            viewModel.postMoveCenter()
        }
    }
}