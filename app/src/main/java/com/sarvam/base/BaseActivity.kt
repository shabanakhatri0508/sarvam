package com.sarvam.base

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.bind
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.sarvam.R
import com.sarvam.databinding.AppLoadingDialogBinding
import com.sarvam.databinding.BaseMainBinding
import com.sarvam.utils.AppUtils
import com.sarvam.utils.constant.AppConstant.MY_PERMISSION_ACCESS_LOCATION


/**
 * Created by Shabana Khatri on 27/02/2020.
 */

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    var mViewModel: BaseViewModel? = null
    private var mProgressDialog: Dialog? = null
    protected var mBinding: T? = null
    private var isLocationPermissionGranted = false
    lateinit var mBaseBinding: BaseMainBinding
    val mActivity: Activity
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBaseBinding = DataBindingUtil.setContentView(this, R.layout.base_main)

        val layoutInflater = LayoutInflater.from(this)
        val view = layoutInflater.inflate(getLayoutResourceId(), null)
        mBaseBinding.container.addView(view)
        mBinding = bind(view)!!
        setSupportActionBar(mBaseBinding.toolbar.toolbar)

        mViewModel = getViewModel()

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

    fun setTitle(title: String) {
        mBaseBinding.toolbar.title.text = title
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
        if (mProgressDialog == null) {
            mProgressDialog = Dialog(this)
            mProgressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
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
        mProgressDialog!!.setContentView(progressDialogBinding.root)

//      setting background of dialog as transparent
        val window = mProgressDialog!!.window
        window?.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent))
//      preventing outside touch and setting cancelable false
        mProgressDialog!!.setCancelable(false)
        mProgressDialog!!.setCanceledOnTouchOutside(false)
        mProgressDialog!!.show()
    }

    private fun hideProgress() {
        mProgressDialog?.run {
            if (isShowing) {
                dismiss()
            }
        }
    }

    protected fun replaceFragment(resId: Int, fragment: BaseFragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(resId, fragment)
        ft.commit()
    }

    protected fun addFragment(resId: Int, fragment: BaseFragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.add(resId, fragment)
        ft.commit()
    }

}
