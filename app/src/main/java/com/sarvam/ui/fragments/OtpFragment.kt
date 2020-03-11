package com.sarvam.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.sarvam.base.BaseFragment
import com.sarvam.base.BaseViewModel
import com.sarvam.databinding.FragmentOtpBinding
import com.sarvam.ui.authentication.AuthenticationViewModel
import com.sarvam.utils.AppUtils

class OtpFragment : BaseFragment() {
        private lateinit var mBinding: FragmentOtpBinding
    private var authenticationViewModel: AuthenticationViewModel? = null
    override fun getViewModel(): BaseViewModel? {
        return ViewModelProvider(this).get(AuthenticationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentOtpBinding.inflate(inflater, container, false)
        activity?.let {
            authenticationViewModel = ViewModelProvider(it).get(AuthenticationViewModel::class.java)
        }
        authenticationViewModel?.phoneNo?.observe(viewLifecycleOwner, Observer {
            AppUtils.printE("Phone No in Otp Screen", it)
        })
        return mBinding.root
    }

}
