package com.sarvam

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sarvam.base.BaseActivity
import com.sarvam.base.BaseViewModel
import com.sarvam.databinding.ActivityMainBinding
import com.sarvam.ui.authentication.AuthenticationViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(getString(R.string.app_name))
    }


    override fun getViewModel(): BaseViewModel? {
        return null
    }

    override fun getLayoutResourceId() = R.layout.activity_main

}