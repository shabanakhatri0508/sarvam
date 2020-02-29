package com.sarvam

import android.os.Bundle
import com.sarvam.base.BaseActivity
import com.sarvam.base.BaseViewModel
import com.sarvam.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
    }


    override fun getViewModel(): BaseViewModel? {
        return null
    }

    override fun getLayoutResourceId() = R.layout.activity_main

}