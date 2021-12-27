package com.hhyun.customalertlibrary.extension

import android.view.View

fun View?.setVisible(isVisible: Boolean) {
    this?.visibility = if(isVisible) View.VISIBLE else View.GONE
}