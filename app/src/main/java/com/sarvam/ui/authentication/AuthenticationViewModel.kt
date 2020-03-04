package com.sarvam.ui.authentication

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sarvam.base.BaseViewModel

class AuthenticationViewModel(application: Application) : BaseViewModel(application) {
    var email: MutableLiveData<String> = MutableLiveData()
    var phoneNo: MutableLiveData<String> = MutableLiveData()
    var verificationCode: MutableLiveData<String> = MutableLiveData()

}