package com.sarvam.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import android.text.TextUtils
import com.sarvam.BuildConfig

object AppUtils {

    /**
     * A method to show device keyboard for user input
     */
    fun showKeyboard(activity: Activity?, view: EditText) {
        showKeyboard(activity as Context, view)
    }

    /**
     * A method to show device keyboard for user input
     */
    fun showKeyboard(context: Context?, view: EditText) {
        try {
            val inputManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } catch (e: Exception) {
            Log.e("Exception showKeyboard", e.toString())
        }
    }

    /**
     * A method to hide the device's keyboard
     */
    fun hideKeyboard(activity: Activity) {
        if (activity.currentFocus != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }

    /**
     * A method which returns the state of internet connectivity of user's phone.
     */
    fun hasInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }


    fun showSnackBar(activity: Activity, view: View, message: String) {
        hideKeyboard(activity)
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    fun printE(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }

    fun printV(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message)
        }
    }

    fun printD(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    fun printI(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

}