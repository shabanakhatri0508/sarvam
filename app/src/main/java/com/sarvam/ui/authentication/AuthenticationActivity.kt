package com.sarvam.ui.authentication

import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sarvam.R
import com.sarvam.base.BaseActivity
import com.sarvam.base.BaseViewModel
import com.sarvam.databinding.ActivityAuthenticationBinding
import com.sarvam.ui.fragments.LoginFragment
import com.sarvam.ui.fragments.OtpFragment

class AuthenticationActivity : BaseActivity<ActivityAuthenticationBinding>() {
    lateinit var authenticationViewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        setTitle(getString(R.string.submit))
        authenticationViewModel = ViewModelProvider.AndroidViewModelFactory(application)
            .create(AuthenticationViewModel::class.java)
        /*load Default fragment*/
        addFragment(R.id.container, LoginFragment())
    }

    override fun getLayoutResourceId() = R.layout.activity_authentication

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider.AndroidViewModelFactory(this.application)
            .create(AuthenticationViewModel::class.java)
    }
}
