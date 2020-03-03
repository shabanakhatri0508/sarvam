package com.sarvam.utils

import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.MutableLiveData

fun EditText.getTextValue(): String {
    return this.text.toString()
}

fun EditText.setText(value: MutableLiveData<String>) {
    setText(value.value)
}

fun TextView.setText(value: MutableLiveData<String>) {
    setText(value.value)
}