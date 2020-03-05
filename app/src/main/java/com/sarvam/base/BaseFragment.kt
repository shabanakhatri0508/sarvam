package com.sarvam.base

import android.animation.ObjectAnimator
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sarvam.R
import com.sarvam.databinding.SimpleDialogBinding
import com.sarvam.utils.AppUtils

abstract class BaseFragment : Fragment() {
    var mViewModel: BaseViewModel? = null
    private var progressDialog: Dialog? = null

    /**
     * @return viewModel instance
     */
    abstract fun getViewModel(): BaseViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        mViewModel?.baseExtras?.observe(this, Observer { baseExtras ->
            if (baseExtras?.apiError != null) {
                AppUtils.showSnackBar(activity!!, getRootView(), baseExtras.apiError!!)
            }
            if (baseExtras?.errorMsg != -1) {
                AppUtils.showSnackBar(
                    activity!!,
                    getRootView(),
                    resources.getString(baseExtras.errorMsg)
                )
            }
            if (baseExtras?.progressBar != null) {
                if (baseExtras.progressBar!!) showProgress() else hideProgress()
            }
        })
    }

    private fun getRootView(): View {
        val contentViewGroup = activity!!.findViewById<View>(android.R.id.content) as ViewGroup
        var rootView = contentViewGroup.getChildAt(0)
        if (rootView == null) rootView = activity!!.window.decorView.rootView
        return rootView!!
    }

    fun showProgress() {
        if (activity != null) {
            if (progressDialog == null) {
                progressDialog = Dialog(activity!!)
                progressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            }

            // inflating and setting view of custom dialog
            val view =
                LayoutInflater.from(context).inflate(R.layout.app_loading_dialog, null, false)
            val imageView = view.findViewById<ImageView>(R.id.imageView2)
            ObjectAnimator.ofFloat(imageView, View.ROTATION, 360f, 0f).apply {
                repeatCount = ObjectAnimator.INFINITE
                duration = 1500
                interpolator = LinearInterpolator()
                start()
            }
            progressDialog!!.setContentView(view)

            // setting background of dialog as transparent
            val window = progressDialog!!.window
            window?.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    context!!,
                    android.R.color.transparent
                )
            )
            // preventing outside touch and setting cancelable false
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.show()
        }
    }

    fun hideProgress() {
        progressDialog?.run {
            if (isShowing) {
                dismiss()
            }
        }
    }

    @Synchronized
    protected fun replaceFragment(resId: Int, fragment: BaseFragment) {
        val ft = fragmentManager?.beginTransaction()
        ft?.replace(resId, fragment)?.commit()
    }

    protected fun addFragment(resId: Int, fragment: BaseFragment) {
        val ft = fragmentManager?.beginTransaction()
        ft?.add(resId, fragment)
        ft?.addToBackStack(fragment::class.java.name)
        ft?.commit()
    }
}