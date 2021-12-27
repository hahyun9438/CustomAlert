package com.hhyun.customalertlibrary

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.hhyun.customalertlibrary.extension.setVisible
import com.hhyun.customalertlibrary.ui.scrollview.MaxHeightScrollView
import com.hhyun.customalertlibrary.ui.shadow.ShadowConstraintLayout
import com.hhyun.customalertlibrary.util.Util
import java.lang.Math.abs

class HhyunAlertController(private val context: Context,
                           private val dialog: AppCompatDialog,
                           private var window: Window?,
                           private val alertType: AlertType) {

    private var tvTitle: TextView? = null
    private var tvDescription: TextView? = null

    private var llContent: ConstraintLayout? = null
    private var llCustomView: LinearLayout? = null
    private var twoButtonContainer: LinearLayout? = null
    private var positiveButton: TextView? = null
    private var negativeButton: TextView? = null
    private var neutralButton: TextView? = null

    fun getAlert(): Dialog = dialog

    fun setLayout(@LayoutRes layoutResId: Int?) {

        window?.requestFeature(Window.FEATURE_NO_TITLE)

        val mLayoutResId = when {
            layoutResId != null -> layoutResId
            alertType == AlertType.BOTTOM || alertType == AlertType.BOTTOM_TRANSPARENT -> R.layout.hhyunlib_alert_bottom_up
            alertType == AlertType.ACTION_SHEET -> R.layout.hhyunlib_alert_action_sheet
            alertType == AlertType.SPINNER_RADIUS_6 -> R.layout.hhyunlib_alert_spinner_radius_6
            alertType == AlertType.SPINNER_RADIUS_10 -> R.layout.hhyunlib_alert_spinner_radius_10
            alertType == AlertType.CENTER || alertType == AlertType.CENTER_TRANSPARENT -> R.layout.hhyunlib_alert_center
            else -> R.layout.hhyunlib_alert_full
        }

        val view = LayoutInflater.from(context).inflate(mLayoutResId, null).apply {
            this.setPadding(0, 0, 0, 0)

            llContent = this.findViewById<ConstraintLayout>(R.id.llContent)
            llCustomView = this.findViewById(R.id.layoutCustom)
            tvTitle = this.findViewById<TextView>(R.id.tvTitle)
            tvDescription = this.findViewById<TextView>(R.id.tvDesc)

            twoButtonContainer = this.findViewById<LinearLayout>(R.id.twoButtonContainer)
            positiveButton = this.findViewById<TextView>(R.id.btnPositive)
            negativeButton = this.findViewById<TextView>(R.id.btnNegative)
            neutralButton = this.findViewById<TextView>(R.id.btnNeutral)
        }

        dialog.setContentView(view)
        setWindowAttributes()

    }

    private fun setWindowAttributes() {

        when(alertType) {

            AlertType.CENTER, AlertType.CENTER_TRANSPARENT -> {
                window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
                window?.setGravity(Gravity.CENTER)
            }

            AlertType.TRANSPARENT,
            AlertType.ACTION_SHEET, AlertType.SPINNER_RADIUS_6, AlertType.SPINNER_RADIUS_10 -> {
                window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                window?.setGravity(Gravity.CENTER)
            }

            else -> {
                window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                window?.setGravity(Gravity.BOTTOM)
                window?.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                )
            }
        }

    }


    fun setTitle(description: Spanned, textSize: Int? = null) {
        tvTitle?.visibility = if(description.isNotEmpty()) View.VISIBLE else View.GONE
        tvTitle?.text = description
        textSize?.let {
            tvTitle?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, it.toFloat())
        }
    }
    fun setDescription(description: Spanned, textSize: Int? = null) {
        tvDescription?.visibility = if(description.isNotEmpty()) View.VISIBLE else View.GONE
        tvDescription?.text = description
        textSize?.let {
            tvDescription?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, it.toFloat())
        }
    }

    fun setPaddingMessageView(topPadding: Int? = null, bottomPadding: Int? = null, gapPadding: Int? = null) {

        val defaultTvVerticalPadding = context.resources.getDimensionPixelSize(R.dimen.hhyunlib_center_alret_gap_padding_default)
        val defaultAlertTopPadding = context.resources.getDimensionPixelSize(R.dimen.hhyunlib_center_alret_vertical_padding_default)
        val defaultAlertBottomPadding = context.resources.getDimensionPixelSize(R.dimen.hhyunlib_center_alret_vertical_padding_default)
        val defaultAlertHorizontalPadding = context.resources.getDimensionPixelSize(R.dimen.hhyunlib_center_alret_horizontal_padding_default)

        val tvVerticalPadding = gapPadding?.let { (Util.dpToPx(context, it) / 2f).toInt() } ?: run { defaultTvVerticalPadding }
        val alertTopPadding = topPadding?.let { Util.dpToPx(context, it) - tvVerticalPadding } ?: run { defaultAlertTopPadding - tvVerticalPadding }
        val alertBottomPadding = bottomPadding?.let { Util.dpToPx(context, it) - tvVerticalPadding } ?: run { defaultAlertBottomPadding - tvVerticalPadding }

        llContent?.setPadding(defaultAlertHorizontalPadding, alertTopPadding, defaultAlertHorizontalPadding, alertBottomPadding)
        tvDescription?.setPadding(0, tvVerticalPadding, 0, tvVerticalPadding)
        tvTitle?.setPadding(0, tvVerticalPadding, 0, tvVerticalPadding)
    }

    fun setMessageView() {
        llContent?.visibility =
            if(tvDescription?.visibility == View.VISIBLE
                || tvDescription?.visibility == View.VISIBLE) View.VISIBLE
            else View.GONE
    }


    fun setCustomView(view: View?) {
        llCustomView?.removeAllViews()
        view?.let { llCustomView?.addView(it) }
    }

    fun setCustomViewSetting(listener: HhyunAlretCustomSettingListener?) {
        listener?.onSetting(dialog)
    }


    fun getButton(buttonType: Int): TextView? {
        return when(buttonType) {
            DialogInterface.BUTTON_POSITIVE -> positiveButton
            DialogInterface.BUTTON_NEGATIVE -> negativeButton
            DialogInterface.BUTTON_NEUTRAL -> neutralButton
            else -> null
        }
    }

    fun setPositiveButton(isCustomDialog: Boolean, label : String,
                          @ColorRes positiveButtonColor: Int = R.color.hhyunlib_black,
                          listener: HhyunAlretButtonClickListener?) {

        val isShowButton = if(isCustomDialog) positiveButton != null else label.isNotEmpty()

        twoButtonContainer?.visibility = if(isShowButton) View.VISIBLE else View.GONE
        positiveButton?.visibility = if(isShowButton) View.VISIBLE else View.GONE

        if(!isCustomDialog && label.isNotEmpty()) positiveButton?.text = label
        if(!isCustomDialog) positiveButton?.setTextColor(ContextCompat.getColor(context, positiveButtonColor))
        positiveButton?.setOnClickListener {
            if(listener != null) listener.onClick(it as TextView, dialog)
            else dialog.dismiss()
        }
    }

    fun setNegativeButton(isCustomDialog: Boolean, label : String,
                          @ColorRes negativeButtonColor: Int = R.color.hhyunlib_black,
                          listener: HhyunAlretButtonClickListener?) {

        val isShowButton = if(isCustomDialog) negativeButton != null else label.isNotEmpty()

        twoButtonContainer?.visibility = if(isShowButton) View.VISIBLE else View.GONE
        negativeButton?.visibility = if(isShowButton) View.VISIBLE else View.GONE

        if(!isCustomDialog && label.isNotEmpty()) negativeButton?.text = label
        if(!isCustomDialog) negativeButton?.setTextColor(ContextCompat.getColor(context, negativeButtonColor))
        negativeButton?.setOnClickListener {
            if(listener != null) listener.onClick(it as TextView, dialog)
            else dialog.dismiss()
        }
    }

    fun setNeutralButton(isCustomDialog: Boolean, label : String,
                         @ColorRes neutralButtonColor: Int = R.color.hhyunlib_black,
                         listener: HhyunAlretButtonClickListener?) {

        val isShowButton = if(isCustomDialog) neutralButton != null else label.isNotEmpty()

        neutralButton?.visibility = if(isShowButton) View.VISIBLE else View.GONE
        if(isShowButton) twoButtonContainer?.visibility = View.GONE

        if(!isCustomDialog && label.isNotEmpty()) neutralButton?.text = label
        if(!isCustomDialog) neutralButton?.setTextColor(ContextCompat.getColor(context, neutralButtonColor))
        neutralButton?.setOnClickListener {
            if(listener != null) listener.onClick(it as TextView, dialog)
            else dialog.dismiss()
        }
    }

    fun setBottomViewLayout(layoutResId: Int,
                            bottomViewBackgroundResId: Int? = null,
                            listener: HhyunBottomAlretCustomSettingListener? = null) {

        val clBottomPopup = dialog.findViewById<ConstraintLayout>(R.id.clPopup)
        val llBottomBody = dialog.findViewById<LinearLayout>(R.id.llBottomBody)

        if(bottomViewBackgroundResId != null) {
            clBottomPopup?.setBackgroundResource(bottomViewBackgroundResId)
        }

        clBottomPopup?.maxHeight = Util.getDeviceHeight(context)

        val bottomView = LayoutInflater.from(context).inflate(layoutResId, null, false).apply {
            this.layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        llBottomBody?.removeAllViews()
        llBottomBody?.addView(bottomView)

        llBottomBody?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                val maxHeight = Util.getDisplayHeight(context)
                val bodyHeight = llBottomBody.measuredHeight

                if(bodyHeight > maxHeight) {
                    val param = llBottomBody.layoutParams
                    param.height = maxHeight
                    llBottomBody.layoutParams = param
                }

                llBottomBody.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        listener?.onSetting(dialog, bottomView)
    }


    fun setActionSheetLayout(anchorView: View?, width: Int, actionList: List<String>,
                             actionMenuGravity: Int = Gravity.CENTER,
                             actionSheetItemTextSize: Int = 15,
                             defaultSelectedActionSheetItem: String? = "",
                             isShowActionSheetArrowIcon: Boolean? = null,
                             topMargin: Int?, rightMargin: Int?,
                             excludeBottomHeight: Int?,
                             listener: HhyunActionSheetClickListener?) {

        val dp5 = Util.dpToPx(context, 5)
        val dp10 = Util.dpToPx(context, 10)
        val dp15 = Util.dpToPx(context, 15)
        val dp20 = Util.dpToPx(context, 20)

        setActionSheetLayout(anchorView, width, actionList, actionMenuGravity, actionSheetItemTextSize,
            defaultSelectedActionSheetItem, isShowActionSheetArrowIcon,
            topMargin, rightMargin,
            dp15, dp5, dp15, dp10, dp10, excludeBottomHeight, listener)

    }


    fun setActionSheetLayout(anchorView: View?, width: Int, actionList: List<String>,
                             actionMenuGravity: Int = Gravity.CENTER,
                             actionSheetItemTextSize: Int = 15,
                             defaultSelectedActionSheetItem: String? = "",
                             isShowActionSheetArrowIcon: Boolean? = null,
                             topMargin: Int?, rightMargin: Int?,
                             leftPadding: Int?, topPadding: Int?, rightPadding: Int?, bottomPadding: Int?,
                             actionSheetItemVerticalPadding: Int?,
                             excludeBottomHeight: Int?,
                             listener: HhyunActionSheetClickListener?) {

        val dp5 = Util.dpToPx(context, 5)
        val dp10 = Util.dpToPx(context, 10)
        val dp15 = Util.dpToPx(context, 15)
        val dp20 = Util.dpToPx(context, 20)

        val aLeftPadding = leftPadding?.let { Util.dpToPx(context, it) } ?: run { dp15 }
        val aRightPadding = rightPadding?.let { Util.dpToPx(context, it) } ?: run { dp15 }
        val aTopPadding = topPadding?.let { Util.dpToPx(context, it) } ?: run { dp5 }
        val aBottomPadding = bottomPadding?.let { Util.dpToPx(context, it) } ?: run { dp10 }
        val aVerticalPadding = actionSheetItemVerticalPadding?.let { Util.dpToPx(context, it) } ?: run { dp10 }

        val contentView = dialog.findViewById<ConstraintLayout>(R.id.clPopupParent)
        val sclSheet = dialog.findViewById<ShadowConstraintLayout>(R.id.sclSheet)
        val clSheet = dialog.findViewById<ConstraintLayout>(R.id.clSheet)
        val sheetContainer = dialog.findViewById<LinearLayout>(R.id.llSheet)
        val ivArrowUp = dialog.findViewById<ImageView>(R.id.ivArrowUp)

        val longestWidth = Util.getTextWidth(context,
            actionList.fold("") { maximum, new -> if(maximum.length < new.length) new else maximum },
            actionSheetItemTextSize).toInt()

        var sheetWidth = if(width > 0) Util.dpToPx(context, width)
        else longestWidth + aLeftPadding + aRightPadding

        if(isShowActionSheetArrowIcon == true) {
            sheetWidth += dp20 + dp5  // 화살표 아이콘 오른쪽 마진 = 10dp, 왼쪽 마진 = 5dp
            ivArrowUp.setVisible(true)
        }

        clSheet?.apply {
            layoutParams.width = sheetWidth
            setPadding(aLeftPadding, 0, aRightPadding, 0)
        }

        sheetContainer?.setPadding(0, aTopPadding, 0, if(actionList.size == 1) aTopPadding else aBottomPadding)
        sheetContainer?.removeAllViews()

        val inflater = LayoutInflater.from(context)
        actionList.forEachIndexed { index, text ->

            val isSelected = text == defaultSelectedActionSheetItem

            val view = inflater.inflate(R.layout.hhyunlib_alret_action_sheet_item, null, false)
            val textView = view.findViewById<TextView>(R.id.tvAction)

            textView.text = text
            textView.gravity = actionMenuGravity
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, actionSheetItemTextSize.toFloat())
            textView.setPadding(0, aVerticalPadding, 0, aVerticalPadding)

            textView.setOnClickListener {
                listener?.onClick(dialog, it, index, text)
            }

            sheetContainer?.addView(view)

            textView?.post {
                val lineCount = textView.layout?.lineCount ?: 0
                if(lineCount > 1) {
                    val param = clSheet?.layoutParams
                    param?.width = sheetWidth + dp5
                    clSheet?.layoutParams = param
                }
            }

        }


        contentView?.viewTreeObserver?.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                var statusBarHeight = Util.getStatusBarHeight(context).toFloat()

                if(statusBarHeight < 1) statusBarHeight = Util.dpToPx(context, 25).toFloat()

                val shadowTop = Util.dpToPx(context, 10).toFloat()
                val shadowHorizontal = Util.dpToPx(context, 15).toFloat()

                val mTopMargin = topMargin?.let {
                    if(it > 0) Util.dpToPx(context, it).toFloat()
                    else Util.dpToPx(context, abs(it)).toFloat() * -1f
                } ?: run { 0f }

                val mRightMargin = rightMargin?.let {
                    if(it > 0) Util.dpToPx(context, it).toFloat()
                    else Util.dpToPx(context, abs(it)).toFloat() * -1f
                } ?: run { 0f }

                val rect = Rect()
                anchorView?.rootView?.getGlobalVisibleRect(rect)

                val targetRect = Rect()
                anchorView?.getGlobalVisibleRect(targetRect)

                val mExcludeBottomHeight = if(excludeBottomHeight != null) Util.dpToPx(context, excludeBottomHeight) else 0

                var y = targetRect.bottom - statusBarHeight - shadowTop + mTopMargin

                if(y >= Util.getDeviceHeight(context) - (sclSheet?.measuredHeight ?: 0) - mExcludeBottomHeight)
                    y -= y + (sclSheet?.measuredHeight ?: 0) + mExcludeBottomHeight - Util.getDeviceHeight(context)

                sclSheet?.x = targetRect.right - sheetWidth - shadowHorizontal - mRightMargin
                sclSheet?.y = y

                contentView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })


        contentView?.setOnClickListener { dialog.dismiss() }

    }

    fun setSpinnerLayout(anchorView: View?,
                         headerView: View,
                         width: Int,
                         spinnerItemTextSize: Int = 15,
                         defaultSelectedSpinnerItem: String? = "",
                         defaultScrollToSpinnerItem: String? = null,
                         spinnerList: List<String>,
                         spinnerMaxHeight: Int? = null,
                         spinnerMaxLength: Int? = null,
                         showAlwaysScrollBar: Boolean = false,
                         listener: HhyunSpinnerClickListener?) {

        val contentView = dialog.findViewById<FrameLayout>(R.id.flPopupParent)
        val sclSpinner = dialog.findViewById<ShadowConstraintLayout>(R.id.sclSpinner)
        val rlSpinner = dialog.findViewById<RelativeLayout>(R.id.rlSpinner)
        val headerContainer = dialog.findViewById<LinearLayout>(R.id.llHeader)
        val scrollView = dialog.findViewById<MaxHeightScrollView>(R.id.sv)
        val spinnerContainer = dialog.findViewById<LinearLayout>(R.id.llSpinner)

        sclSpinner?.apply {
            layoutParams.width = Util.dpToPx(context, width) + Util.dpToPx(context, 30)
        }

        headerContainer?.apply {
            removeAllViews()
            addView(headerView)
            setOnClickListener { dialog.dismiss() }
        }

        val inflater = LayoutInflater.from(context)
        val viewList = ArrayList<View>()
        val textViewList = ArrayList<TextView>()

        spinnerContainer?.removeAllViews()
        spinnerList.forEachIndexed { index, text ->

            val itemResId = if(alertType == AlertType.SPINNER_RADIUS_6) R.layout.hhyunlib_alret_spinner_radius_6_item
            else R.layout.hhyunlib_alret_spinner_radius_10_item

            val view = inflater.inflate(itemResId, null, false)
            val textView = view.findViewById<TextView>(R.id.tvSpinner)

            val isSelected = text == defaultSelectedSpinnerItem

            textView.text = text
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, spinnerItemTextSize.toFloat())

            textView.setOnClickListener { v ->

                textViewList.map {
                    it.setVisible(it.text == (v as TextView).text)
                }

                listener?.onClick(dialog, view, index, text)

            }

            spinnerMaxLength?.let {
                textView.maxLines = it
                textView.ellipsize = TextUtils.TruncateAt.END
            }

            viewList.add(view)
            textViewList.add(textView)

            spinnerContainer?.addView(view)
        }

        if(showAlwaysScrollBar) {
            scrollView?.isScrollbarFadingEnabled = false
            scrollView?.isVerticalScrollBarEnabled = true
        }

        val scrollToPosition = when {
            !defaultScrollToSpinnerItem.isNullOrEmpty() -> spinnerList.indexOfFirst { it == defaultScrollToSpinnerItem }
            !defaultSelectedSpinnerItem.isNullOrEmpty() -> spinnerList.indexOfFirst { it == defaultSelectedSpinnerItem }
            else -> 0
        }
        val defaultSelectedView = viewList.getOrNull(scrollToPosition)
        if(defaultSelectedView != null) {
            scrollView?.postDelayed({
                val top = defaultSelectedView.top
                scrollView.smoothScrollTo(0, top)
            }, 20)
        }

        contentView?.viewTreeObserver?.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                val deviceHeight = Util.getDeviceHeight(context) - Util.dpToPx(context, 20) - Util.getNavigationBarHeight(context)
                val center = deviceHeight / 2
                val statusBarHeight = Util.getStatusBarHeight(context)

                val rect = Rect()
                anchorView?.rootView?.getGlobalVisibleRect(rect)

                val targetRect = Rect()
                anchorView?.getGlobalVisibleRect(targetRect)

                val minusY = rect.top
                rect.top -= minusY
                rect.bottom -=minusY
                targetRect.top -= minusY
                targetRect.bottom -= minusY

                val targetViewHeight = targetRect.bottom - targetRect.top
                val isUpperZone = targetRect.bottom >= center
                val bottomZoneHeight = deviceHeight - targetRect.bottom
                var popupHeight = sclSpinner?.measuredHeight ?: 0

                val isAbove = isUpperZone && popupHeight > bottomZoneHeight

                var maxHeight = if(isAbove) targetRect.bottom / 2
                else targetViewHeight + bottomZoneHeight - statusBarHeight

                if(spinnerMaxHeight != null && maxHeight > Util.dpToPx(context, spinnerMaxHeight)) {
                    maxHeight = Util.dpToPx(context, spinnerMaxHeight)
                }

                val margin = if(isAbove) Util.dpToPx(context, 20).toFloat() else Util.dpToPx(context, 10).toFloat()

                val y = if(isAbove) targetRect.bottom - popupHeight - margin
                else targetRect.top - margin - statusBarHeight.toFloat()

                sclSpinner?.x = targetRect.left - Util.dpToPx(context, 15).toFloat()
                sclSpinner?.y = y

                scrollView?.apply {
                    setMaxHeight(maxHeight)
                    requestLayout()
                }

                if(isAbove) {
                    Handler(Looper.getMainLooper()).post {
                        popupHeight = rlSpinner?.measuredHeight ?: 0
                        sclSpinner?.y = targetRect.bottom - popupHeight - (margin * 2)
                    }
                }

                contentView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        contentView?.setOnClickListener { dialog.dismiss() }

    }


    fun setInfoViewLayout(anchorView: View?, layoutResId: Int, topMargin: Int?) {

        val contentView = dialog.findViewById<FrameLayout>(R.id.flPopupParent)
        val llInfo = dialog.findViewById<LinearLayout>(R.id.llInfo)

        llInfo?.removeAllViews()
        val inflater = LayoutInflater.from(context)
        val addView = inflater.inflate(layoutResId, null, false)
        llInfo?.addView(addView)

        contentView?.viewTreeObserver?.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                val mTopMargin = topMargin?.let {
                    if(it > 0) Util.dpToPx(context, it).toFloat()
                    else Util.dpToPx(context, abs(it)).toFloat() * -1f
                } ?: run { 0f }

                val statusBarHeight = Util.getStatusBarHeight(context)

                val rect = Rect()
                anchorView?.rootView?.getGlobalVisibleRect(rect)

                val targetRect = Rect()
                anchorView?.getGlobalVisibleRect(targetRect)

                val minusY = rect.top
                rect.top -= minusY
                rect.bottom -=minusY
                targetRect.top -= minusY
                targetRect.bottom -= minusY

                var y = targetRect.bottom - statusBarHeight + mTopMargin

                if(y >= Util.getDeviceHeight(context) - (llInfo?.measuredHeight ?: 0))
                    y -= y + (llInfo?.measuredHeight ?: 0) - Util.getDeviceHeight(context)

                llInfo?.x = 0f
                llInfo?.y = y

                contentView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        contentView?.setOnClickListener { dialog.dismiss() }
    }



    class AlertParam {

        var backPressCancelable: Boolean = false
        var outsideTouchCancelable: Boolean = false

        var cancelListener: HhyunAlretCancelListener? = null


        var layoutResId: Int? = null

        var customView: View? = null
        var customSettingListener: HhyunAlretCustomSettingListener? = null


        var topPadding: Int? = null
        var bottomPadding: Int? = null
        var leftPadding: Int? = null
        var rightPadding: Int? = null

        var topMargin: Int? = 0
        var bottomMargin: Int? = 0
        var leftMargin: Int? = 0
        var rightMargin: Int? = 0


        var title: Spanned = SpannableString("")
        var titleSize: Int? = null
        var description: Spanned = SpannableString("")
        var descriptionSize: Int? = null
        var messageGapPadding: Int? = null

        var positiveButtonLabel: String = ""
        var positiveButtonColor: Int = R.color.hhyunlib_black
        var positiveButtonClickListener: HhyunAlretButtonClickListener? = null

        var negativeButtonLabel: String = ""
        var negativeButtonColor: Int = R.color.hhyunlib_black
        var negativeButtonClickListener: HhyunAlretButtonClickListener? = null

        var neutralButtonLabel: String = ""
        var neutralButtonColor: Int = R.color.hhyunlib_black
        var neutralButtonClickListener: HhyunAlretButtonClickListener? = null

        var bottomViewLayoutResId: Int? = null
        var bottomCustomSettingListener: HhyunBottomAlretCustomSettingListener? = null
        var bottomViewBackgroundResId: Int? = null

        var width: Int = 0
        var maxHeight: Int? = null
        var maxLength: Int? = null
        var excludeBottomHeight: Int? = 0

        var anchorView: View? = null

        var textSize: Int = 15

        var actionList: List<String> = ArrayList()
        var actionMenuGravity: Int = Gravity.CENTER
        var actionSheetListener: HhyunActionSheetClickListener? = null
        var defaultSelectedActionSheetItem: String? = ""
        var isShowActionSheetArrowIcon: Boolean? = null
        var actionSheetItemVerticalPadding: Int? = 0

        var spinnerHeaderView: View? = null
        var defaultSelectedSpinnerItem: String? = null
        var defaultScrollToSpinnerItem: String? = null
        var spinnerList: List<String> = ArrayList()
        var spinnerListener: HhyunSpinnerClickListener? = null

        var showAlwaysScrollBar: Boolean = false

        var infoViewLayoutResId: Int? = null


        fun apply(controller: HhyunAlertController) {

            val isCustomDialog = isCustomDialog()

            controller.setLayout(layoutResId)

            description.takeIf { it.isNotEmpty() }?.let { controller.setDescription(it, descriptionSize) }
            title.takeIf { it.isNotEmpty() }?.let { controller.setTitle(it, titleSize) }
            controller.setPaddingMessageView(topPadding, bottomPadding, messageGapPadding)

            customView?.let {
                controller.setMessageView()
                controller.setCustomView(it)
            }

            customSettingListener?.let { controller.setCustomViewSetting(it) }

            controller.setPositiveButton(isCustomDialog, positiveButtonLabel, positiveButtonColor, positiveButtonClickListener)
            controller.setNegativeButton(isCustomDialog, negativeButtonLabel, negativeButtonColor, negativeButtonClickListener)
            controller.setNeutralButton(isCustomDialog, neutralButtonLabel, neutralButtonColor, neutralButtonClickListener)

            bottomViewLayoutResId?.takeIf { it > 0 }?.let {
                controller.setBottomViewLayout(it, bottomViewBackgroundResId, bottomCustomSettingListener)
            }

            actionList.takeIf { it.isNotEmpty() }?.let {
                controller.setActionSheetLayout(anchorView, width, it, actionMenuGravity,
                    textSize, defaultSelectedActionSheetItem, isShowActionSheetArrowIcon,
                    topMargin, rightMargin, leftPadding, topPadding, rightPadding, bottomPadding,
                    actionSheetItemVerticalPadding,
                    excludeBottomHeight, actionSheetListener)
            }

            spinnerHeaderView?.let {
                controller.setSpinnerLayout(anchorView, it, width, textSize,
                    defaultSelectedSpinnerItem, defaultScrollToSpinnerItem,
                    spinnerList, maxHeight, maxLength, showAlwaysScrollBar, spinnerListener)
            }

            infoViewLayoutResId?.takeIf { it > 0 }?.let {
                controller.setInfoViewLayout(anchorView, it, topMargin)
            }

        }

        private fun isCustomDialog(): Boolean {
            val isExisted = layoutResId != null && layoutResId!! > 0
            return isExisted
                    && layoutResId != R.layout.hhyunlib_alert_bottom_up
                    && layoutResId != R.layout.hhyunlib_alert_center
        }

    }

}