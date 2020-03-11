package com.sarvam.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.sarvam.R
import com.sarvam.base.BaseFragment
import com.sarvam.base.BaseViewModel
import com.sarvam.databinding.FragmentLoginBinding
import com.sarvam.ui.authentication.AuthenticationViewModel

class LoginFragment : BaseFragment(), View.OnClickListener {
    private lateinit var mBinding: FragmentLoginBinding
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
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)
        init()
        return mBinding.root
    }

    private fun init() {
        /*Initialize ViewModel here*/
        activity?.let {
            authenticationViewModel = ViewModelProvider(it).get(AuthenticationViewModel::class.java)
        }
        mBinding.viewModel = authenticationViewModel
        mBinding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_submit -> {
                replaceFragment(R.id.container, OtpFragment(), null)
            }
        }
    }
}
