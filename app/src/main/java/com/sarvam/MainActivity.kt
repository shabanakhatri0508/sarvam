package com.sarvam

import android.content.Intent
import android.os.Bundle
import com.sarvam.base.BaseActivity
import com.sarvam.base.BaseViewModel
import com.sarvam.databinding.ActivityMainBinding
import com.sarvam.ui.PhoneAuthActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(getString(R.string.app_name))


        startActivity(Intent(applicationContext, PhoneAuthActivity::class.java))
    }


    override fun getViewModel(): BaseViewModel? {
        return null
    }

    override fun getLayoutResourceId() = R.layout.activity_main

}