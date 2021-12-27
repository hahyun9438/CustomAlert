package com.hhyun.customalertlibrary

import android.app.Dialog
import android.view.View
import android.widget.TextView

interface HhyunAlretCustomSettingListener {
    fun onSetting(dialog: Dialog)
}

interface HhyunBottomAlretCustomSettingListener {
    fun onSetting(dialog: Dialog, parentView: View?)
    fun closeAlert()
}

interface HhyunAlretButtonClickListener {
    fun onClick(view: TextView, dialog: Dialog)
}

interface HhyunAlretCancelListener {
    fun onCancel(dialog: Dialog)
}

interface HhyunActionSheetClickListener {
    fun onClick(dialog: Dialog, view: View, selectIndex: Int, selectName: String)
}

interface HhyunSpinnerClickListener {
    fun onClick(dialog: Dialog, view: View, selectIndex: Int, selectName: String)
}