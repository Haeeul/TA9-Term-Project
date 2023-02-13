package com.example.mh_term_app.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
    lateinit var viewDataBinding: VB

    abstract val layoutResID: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResID)
        viewDataBinding.lifecycleOwner = this@BaseActivity

        initView()
    }

    open fun initView() {}
}