package com.sarvam.base

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.sarvam.R
import com.sarvam.databinding.AppLoadingDialogBinding
import com.sarvam.utils.AppUtils

/**
 * Created by Shabana Khatri on 27/02/2020.
 */

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    var mViewModel: BaseViewModel? = null
    private var progressDialog: Dialog? = null
    lateinit var baseBinding: T

    companion object {
        const val MY_PERMISSION_ACCESS_LOCATION = 100
        var isLocationPermissionGranted = false
    }

    val mActivity: Activity
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding = DataBindingUtil.setContentView(this, getLayoutResourceId())

        mViewModel?.baseExtras?.observe(this, Observer { baseExtras ->
            if (baseExtras?.apiError != null) {
                AppUtils.showSnackBar(this, getRootView(), baseExtras.apiError!!)
            }
            if (baseExtras?.errorMsg != -1) {
                AppUtils.showSnackBar(this, getRootView(), resources.getString(baseExtras.errorMsg))
            }
            if (baseExtras?.progressBar != null) {
                if (baseExtras.progressBar!!) showProgress() else hideProgress()
            }
        })
    }

    abstract fun getLayoutResourceId(): Int

    /**
     * @return viewModel instance
     */
    abstract fun getViewModel(): BaseViewModel?

    protected fun getRootView(): View {
        val contentViewGroup = findViewById<View>(android.R.id.content) as ViewGroup
        var rootView = contentViewGroup.getChildAt(0)
        if (rootView == null) rootView = window.decorView.rootView
        return rootView!!
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSION_ACCESS_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AppUtils.printE("Permission Result", "Permission Granted")
                    isLocationPermissionGranted = true
                } else {
                    isLocationPermissionGranted = false
                    AppUtils.printE("Permission Result", "Permission Denied")
                }
                return
            }
        }
    }

    fun showProgress() {
        if (progressDialog == null) {
            progressDialog = Dialog(this)
            progressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        // inflating and setting view of custom dialog
        val progressDialogBinding =
            AppLoadingDialogBinding.inflate(LayoutInflater.from(this), null, false)
        ObjectAnimator.ofFloat(progressDialogBinding.imageView2, View.ROTATION, 360f, 0f).apply {
            repeatCount = ObjectAnimator.INFINITE
            duration = 1500
            interpolator = LinearInterpolator()
            start()
        }
        progressDialog!!.setContentView(progressDialogBinding.root)

//      setting background of dialog as transparent
        val window = progressDialog!!.window
        window?.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent))
//      preventing outside touch and setting cancelable false
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.show()
    }

    private fun hideProgress() {
        progressDialog?.run {
            if (isShowing) {
                dismiss()
            }
        }
    }

}
