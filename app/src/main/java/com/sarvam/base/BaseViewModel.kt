package com.sarvam.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sarvam.R
import com.sarvam.models.BaseExtras
import com.sarvam.webservices.ApiClient

class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val baseExtras: MutableLiveData<BaseExtras> by lazy {
        return@lazy MutableLiveData<BaseExtras>()
    }

    fun onApiStart() {
        baseExtras.postValue(BaseExtras(true))
    }

    fun onInternetError() {
        baseExtras.value = BaseExtras(R.string.msg_no_internet)
    }

    fun onApiFinish() {
        baseExtras.value = BaseExtras(false)
    }

    fun onApiError(apiErrorMsg: String) {
        baseExtras.value = BaseExtras(apiErrorMsg)
    }

    fun getApiService() = ApiClient().getApiService()

}