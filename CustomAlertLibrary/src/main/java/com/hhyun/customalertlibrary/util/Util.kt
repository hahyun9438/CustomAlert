package com.hhyun.customalertlibrary.util

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager

object Util {

    @JvmStatic
    fun dpToPx(context: Context, dp: Float): Int {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
    }

    @JvmStatic
    fun dpToPx(context: Context, dp: Int): Int {
        return dpToPx(context, dp.toFloat())
    }

    @JvmStatic
    fun pxToDp(context: Context, px: Float): Int {
        val metrics = context.resources.displayMetrics
        return (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    @JvmStatic
    fun pxToDp(context: Context, px: Int): Int {
        return pxToDp(context, px.toFloat())
    }

    @JvmStatic
    fun getDeviceWidth(context: Context?): Int {
        val dm = context?.resources?.displayMetrics
        return dm?.widthPixels ?: 0
    }

    @JvmStatic
    fun getDeviceHeight(context: Context?): Int {
        val dm = context?.resources?.displayMetrics
        return dm?.heightPixels ?: 0
    }

    @JvmStatic
    fun getDisplayHeight(context: Context?): Int {
        val wm = context?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        val dm = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm?.defaultDisplay?.getRealMetrics(dm)
        } else {
            wm?.defaultDisplay?.getMetrics(dm)
        }
        var height: Int = dm.heightPixels - getStatusBarHeight(context)
        if (isSoftNavigationBar(context)) height -= getNavigationBarHeight(context)

        return height
    }

    @JvmStatic
    fun getStatusBarHeight(context: Context?): Int {
        var result = 0
        val resourceId = context?.resources?.getIdentifier("status_bar_height", "dimen", "android")
        if ((resourceId ?: 0) > 0) {
            result = context?.resources?.getDimensionPixelSize(resourceId!!) ?: 0
        }
        return result
    }

    @JvmStatic
    fun getNavigationBarHeight(context: Context?): Int {
        val resources = context?.resources
        val resourceId = resources?.getIdentifier("navigation_bar_height", "dimen", "android")
        return if ((resourceId ?: 0) > 0) {
            resources?.getDimensionPixelSize(resourceId!!) ?: 0
        } else 0
    }

    @JvmStatic
    fun isSoftNavigationBar(context: Context?): Boolean {
        val id = context?.resources?.getIdentifier("config_showNavigationBar", "bool", "android")
        var isUse = false
        if ((id ?: 0) > 0) isUse = context?.resources?.getBoolean(id!!) ?: false
        return isUse
    }


    @JvmStatic
    fun getTextWidth(context: Context, text: CharSequence?, textSizeOfDp: Int): Float {
        if(text.isNullOrEmpty()) return 0f
        val paint = Paint().apply {
            isAntiAlias = true
            textSize = dpToPx(context, textSizeOfDp).toFloat()
        }

        val characterWidths = FloatArray(text.length)
        paint.getTextWidths(text.toString(), characterWidths)

        return characterWidths.sum()
    }
}